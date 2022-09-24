package com.friday.peanutbutter.service;

import com.friday.peanutbutter.dto.PaginationDTO;
import com.friday.peanutbutter.dto.ThreadDTO;
import com.friday.peanutbutter.dto.ThreadQueryDTO;
import com.friday.peanutbutter.exception.CustomizeErrorCode;
import com.friday.peanutbutter.exception.CustomizeException;
import com.friday.peanutbutter.mapper.ThreadExtMapper;
import com.friday.peanutbutter.mapper.ThreadMapper;
import com.friday.peanutbutter.mapper.UserMapper;
import com.friday.peanutbutter.model.PostThread;
import com.friday.peanutbutter.model.PostThreadExample;
import com.friday.peanutbutter.model.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ThreadService {

    @Autowired
    private ThreadMapper threadMapper;

    @Autowired
    private ThreadExtMapper threadExtMapper;

    @Autowired
    private UserMapper userMapper;

    //这里大量重用可优化
    public PaginationDTO list(String search,Integer page, Integer size) {
        PaginationDTO<ThreadDTO> paginationDTO = new PaginationDTO();
        if (StringUtils.isNotBlank(search)){
            String[] tags = StringUtils.split(search," ");
            search = Arrays.stream(tags).collect(Collectors.joining("|"));
        }else{
            search=null;
        }
        //包括关键词的全局搜索
        ThreadQueryDTO threadQueryDTO = new ThreadQueryDTO();
        threadQueryDTO.setSearch(search);
        Integer totalCount = threadExtMapper.countBySearch(threadQueryDTO);

        if(totalCount==0) {return paginationDTO;}
        if(totalCount%size==0){
            paginationDTO.setTotalPage(totalCount/size);

        }else{
            paginationDTO.setTotalPage(totalCount/size+1);
        }
        //作容错处理，防止访问越界页面
        if(page<1){
            page=1;
        }
        if(page>paginationDTO.getTotalPage()){
            //当totalpage为0的时候会出bug
            page= paginationDTO.getTotalPage();
        }
        paginationDTO.setPagination(page);
        //不同页数情况参数的设定
        Integer offset = size*(page-1);
        threadQueryDTO.setSize(size);
        threadQueryDTO.setPage(offset);
        List<PostThread> postThreads = threadExtMapper.selectBySearch(threadQueryDTO);
        List<ThreadDTO> threadDTOList = new ArrayList<>();

        for(PostThread postThread :postThreads){
            //根据帖子的creator属性找到对应User的头像
            User user = userMapper.selectByPrimaryKey(postThread.getCreator());
            //转换成ThreadDTO
            ThreadDTO threadDTO = new ThreadDTO();
            //使用该工具类快速构造
            BeanUtils.copyProperties(postThread,threadDTO);
            threadDTO.setUser(user);
            threadDTOList.add(threadDTO);
        }
        paginationDTO.setData(threadDTOList);

        return paginationDTO;
    }

    public PaginationDTO list(Long userId,Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();

        Integer totalPage;

        PostThreadExample postThreadExample = new PostThreadExample();
        postThreadExample.createCriteria()
                .andCreatorEqualTo(userId);
        Integer totalCount = (int) threadMapper.countByExample(postThreadExample);

        if(totalCount==0)return paginationDTO;
        if(totalCount%size==0){
            paginationDTO.setTotalPage(totalCount/size);

        }else{
            paginationDTO.setTotalPage(totalCount/size+1);
        }
        //作容错处理，防止访问越界页面
        if(page<1){
            page=1;
        }
        if(page>paginationDTO.getTotalPage()){
            //当totalpage为0的时候会出bug
            page= paginationDTO.getTotalPage();
        }
        paginationDTO.setPagination(page);

        //size*(page-1)
        Integer offset = size * (page - 1);
        PostThreadExample example = new PostThreadExample();
        example.createCriteria()
                .andCreatorEqualTo(userId);
        List<PostThread> postThreads = threadMapper.selectByExampleWithRowbounds(example, new RowBounds(offset, size));
        List<ThreadDTO> threadDTOList = new ArrayList<>();

        for(PostThread postThread :postThreads){
            //根据帖子的creator属性找到对应User的头像
            User user = userMapper.selectByPrimaryKey(postThread.getCreator());
            //转换成ThreadDTO
            ThreadDTO threadDTO = new ThreadDTO();
            //使用该工具类快速构造
            BeanUtils.copyProperties(postThread,threadDTO);
            threadDTO.setUser(user);
            threadDTOList.add(threadDTO);
        }
        paginationDTO.setData(threadDTOList);

        return paginationDTO;
    }

    public ThreadDTO getById(Long id){
        PostThread postThread = threadMapper.selectByPrimaryKey(id);
        if(postThread == null){
            //使用枚举的方式
            throw new CustomizeException(CustomizeErrorCode.THREAD_NOT_FOUND);
        }
        ThreadDTO threadDTO = new ThreadDTO();
        BeanUtils.copyProperties(postThread,threadDTO);
        User user = userMapper.selectByPrimaryKey(threadDTO.getCreator());
        threadDTO.setUser(user);

        return threadDTO;

    }

    public void createOrUpdate(PostThread postThread) {
        //如果id为null则是创建一篇新的帖子
        if(postThread.getId()==null){
            //创建时设置时间
            postThread.setGmtCreate(System.currentTimeMillis());
            postThread.setGmtModified(postThread.getGmtCreate());
            postThread.setCommentCount(0);
            postThread.setLikeCount(0);
            threadMapper.insert(postThread);
        }else {
            //修改帖子
            PostThread updateThread =  new PostThread();
            updateThread.setGmtModified(System.currentTimeMillis());
            updateThread.setTitle(postThread.getTitle());
            updateThread.setDescription(postThread.getDescription());
            updateThread.setTag(postThread.getTag());
            //使用封装的类
            PostThreadExample postThreadExample = new PostThreadExample();
            postThreadExample.createCriteria().andIdEqualTo(postThread.getId());
            threadMapper.updateByExampleSelective(updateThread,postThreadExample);

        }
    }

    public void incView(Long id) {
        PostThread postThread = new PostThread();
        postThread.setId(id);
        postThread.setViewCount(1);

        threadExtMapper.incView(postThread);
    }
    //根据标签找到相关的帖子
    public List<ThreadDTO> selectRelated(ThreadDTO queryDTO) {
        if(StringUtils.isBlank(queryDTO.getTag())){
            return new ArrayList<>();
        }
        String[] tags = StringUtils.split(queryDTO.getTag(),"/");
        String regexpTag = Arrays.stream(tags).collect(Collectors.joining("|"));
        PostThread postThread = new PostThread();
        postThread.setId(queryDTO.getId());
        postThread.setTag(regexpTag);

        List<PostThread> postThreads = threadExtMapper.selectRelated(postThread);
        //把找到的postthread映射到threadDTO
        List<ThreadDTO> threadDTOS =postThreads.stream().map(q->{
            ThreadDTO threadDTO = new ThreadDTO();
            BeanUtils.copyProperties(q,threadDTO);
            return threadDTO;
        }).collect(Collectors.toList());
        return threadDTOS;

    }
}
