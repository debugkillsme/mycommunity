package com.friday.peanutbutter.service;

import com.friday.peanutbutter.dto.CommentDTO;
import com.friday.peanutbutter.enums.CommentTypeEnum;
import com.friday.peanutbutter.enums.NotificationStatusEnum;
import com.friday.peanutbutter.enums.NotificationTypeEnum;
import com.friday.peanutbutter.exception.CustomizeErrorCode;
import com.friday.peanutbutter.exception.CustomizeException;
import com.friday.peanutbutter.mapper.*;
import com.friday.peanutbutter.model.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CommentService {
    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private ThreadMapper threadMapper;

    @Autowired
    private ThreadExtMapper threadExtMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CommentExtMapper commentExtMapper;

    @Autowired
    private NotificationMapper notificationMapper;

    @Transactional
    //如果方法体中有语句出错，整个事务实现回滚
    public void insert(Comment comment, User commentator) {
        if(comment.getParentId()==null||comment.getParentId() == 0){
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }

        if(comment.getType() == null|| !CommentTypeEnum.isExist(comment.getType())){
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
        }

        if(comment.getType() == CommentTypeEnum.COMMENT.getType()){
            //回复评论,找到对应的评论
            Comment dbComment = commentMapper.selectByPrimaryKey(comment.getParentId());
            if(dbComment == null){
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
            //找到对应的帖子
            PostThread postThread = threadMapper.selectByPrimaryKey(dbComment.getParentId());
            if(postThread == null){
                throw new CustomizeException(CustomizeErrorCode.THREAD_NOT_FOUND);
            }
            commentMapper.insert(comment);
            //被回复的评论的评论数加1
            Comment parentComment = new Comment();
            parentComment.setId(comment.getParentId());
            parentComment.setCommentCount(1);
            commentExtMapper.incCommentCount(parentComment);
            createNotify(comment, dbComment.getCommentator(),commentator.getName(),
                    postThread.getTitle(),NotificationTypeEnum.REPLY_COMMENT,postThread.getId());
            //创建通知

        }else {
            //回复帖子
            PostThread postThread = threadMapper.selectByPrimaryKey(comment.getParentId());
            if(postThread == null){
                throw new CustomizeException(CustomizeErrorCode.THREAD_NOT_FOUND);
            }
            commentMapper.insert(comment);
            postThread.setCommentCount(1);
            threadExtMapper.incCommentCount(postThread);
            createNotify(comment,postThread.getCreator(),commentator.getName(),
                    postThread.getTitle(),NotificationTypeEnum.REPLY_POST,postThread.getId() );

        }
    }

    private void createNotify(Comment comment, Long receiver, String notifierName, String outerTitle, NotificationTypeEnum notificationType, Long outerId) {
        Notification notification = new Notification();
        notification.setGmtCreate(System.currentTimeMillis());
        notification.setType(notificationType.getType());
        //外键就是commentId
        notification.setOuterId(outerId);
        notification.setNotifier(comment.getCommentator());
        notification.setStatus(NotificationStatusEnum.UNREAD.getStatus());
        //被回复的评论的commentator就是receiver
        notification.setReceiver(receiver);
        //传入这两个参数在通知页面显示
        notification.setNotifierName(notifierName);
        notification.setOuterTitle(outerTitle);
        notificationMapper.insert(notification);
    }

    //获取关于帖子或者评论的评论
    public List<CommentDTO> listByParentId(Long id, CommentTypeEnum commentTypeEnum) {
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria()
                .andParentIdEqualTo(id)
                .andTypeEqualTo(commentTypeEnum.getType());
        //评论按照倒叙排序
        commentExample.setOrderByClause("gmt_create desc");
        List<Comment> comments = commentMapper.selectByExample(commentExample);
        if(comments.size() == 0){
            return new ArrayList<>();
        }
        //lambda表达式获取去重的评论人
        Set<Long> commentators = comments.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());
        List<Long> userIds = new ArrayList<>();
        userIds.addAll(commentators);

        //获取评论人并转换为Map
        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andIdIn(userIds);
        List<User> users = userMapper.selectByExample(userExample);
        Map<Long,User> userMap = users.stream().collect(Collectors.toMap(user -> user.getId(),user -> user));

        //转换comment为commentDTO,函数式编程
        List<CommentDTO> commentDTOS = comments.stream().map(comment -> {
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment,commentDTO);
            commentDTO.setUser(userMap.get(comment.getCommentator()));
            return commentDTO;
        }).collect(Collectors.toList());

        return commentDTOS;
    }
}
