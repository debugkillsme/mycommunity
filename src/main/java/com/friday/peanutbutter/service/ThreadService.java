package com.friday.peanutbutter.service;

import com.friday.peanutbutter.exception.CustomizeErrorCode;
import com.friday.peanutbutter.exception.CustomizeException;
import com.friday.peanutbutter.mapper.ThreadExtMapper;
import com.friday.peanutbutter.mapper.ThreadMapper;
import com.friday.peanutbutter.mapper.UserMapper;
import com.friday.peanutbutter.model.PostThread;
import com.friday.peanutbutter.model.PostThreadExample;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
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

    //这里用PageHelper插件优化了一下
    public PageInfo<PostThread> list(String search, Integer page, Integer size) {
        if (StringUtils.isNotBlank(search)){
            String[] tags = StringUtils.split(search," ");
            search = Arrays.stream(tags).collect(Collectors.joining("|"));
        }else{
            search=null;
        }
        //包括关键词的全局搜索


        PageHelper.startPage(page,size);
        List<PostThread> postThreads = threadExtMapper.selectBySearch(search);
        PageInfo<PostThread> postThreadPageInfo = new PageInfo<>(postThreads);

        return postThreadPageInfo;
    }

    public PageInfo<PostThread> list(Long userId,Integer page, Integer size) {
        PostThreadExample postThreadExample = new PostThreadExample();
        postThreadExample.createCriteria()
                .andCreatorEqualTo(userId);

        PageHelper.startPage(page,size);
        List<PostThread> postThreads = threadMapper.selectByExample(postThreadExample);
        PageInfo<PostThread> postThreadPageInfo = new PageInfo<>(postThreads);
        return postThreadPageInfo;
    }

    public PostThread getById(Long id){
        PostThread postThread = threadMapper.selectByPrimaryKey(id);
        if(postThread == null){
            //使用枚举的方式
            throw new CustomizeException(CustomizeErrorCode.THREAD_NOT_FOUND);
        }

        return postThread;

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
    public List<PostThread> selectRelated(PostThread curPostThread) {
        if(StringUtils.isBlank(curPostThread.getTag())){
            return new ArrayList<>();
        }
        String[] tags = StringUtils.split(curPostThread.getTag(),"/");
        String regexpTag = Arrays.stream(tags).collect(Collectors.joining("|"));
        PostThread postThread = new PostThread();
        postThread.setId(postThread.getId());
        postThread.setTag(regexpTag);

        List<PostThread> postThreads = threadExtMapper.selectRelated(postThread);

        return postThreads;

    }
}
