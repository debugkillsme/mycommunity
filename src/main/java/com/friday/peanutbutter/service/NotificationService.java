package com.friday.peanutbutter.service;

import com.friday.peanutbutter.dto.NotificationDTO;
import com.friday.peanutbutter.enums.NotificationStatusEnum;
import com.friday.peanutbutter.enums.NotificationTypeEnum;
import com.friday.peanutbutter.exception.CustomizeErrorCode;
import com.friday.peanutbutter.exception.CustomizeException;
import com.friday.peanutbutter.mapper.NotificationMapper;
import com.friday.peanutbutter.model.Notification;
import com.friday.peanutbutter.model.NotificationExample;
import com.friday.peanutbutter.model.User;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class NotificationService {

    @Autowired
    private NotificationMapper notificationMapper;

    public PageInfo<NotificationDTO> list(Long userId, Integer page, Integer size) {
        //Integer totalCount = threadMapper.countByUserId(userId);
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria().
                andReceiverEqualTo(userId);
        //按照降序排列将未读消息放在上面
        notificationExample.setOrderByClause("gmt_create desc");
        PageHelper.startPage(page,size);
        List<Notification> notifications = notificationMapper.selectByExample(notificationExample);



        List<NotificationDTO> notificationDTOS = new ArrayList<>();
        for(Notification notification:notifications){
            NotificationDTO notificationDTO = new NotificationDTO();
            BeanUtils.copyProperties(notification,notificationDTO);
            notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
            notificationDTOS.add(notificationDTO);
        }
        PageInfo<NotificationDTO> paginationDTO = new PageInfo<>(notificationDTOS);
        return paginationDTO;
    }

    //获得未阅读数量
    public Long unreadCount(Long userId) {
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria()
                .andReceiverEqualTo(userId)
                .andStatusEqualTo(NotificationStatusEnum.UNREAD.getStatus());
        return notificationMapper.countByExample(notificationExample);
    }

    public NotificationDTO read(Long id, User user){
        Notification notification = notificationMapper.selectByPrimaryKey(id);
        if(notification==null){
            throw new CustomizeException(CustomizeErrorCode.NOTIFICATION_NOT_FOUND);
        }

        if(!Objects.equals(notification.getReceiver(),user.getId())) {
            throw new CustomizeException(CustomizeErrorCode.READ_NOTIFICATION_FAILED);
        }
        //拿到通知之后将状态改成已读
        notification.setStatus(NotificationStatusEnum.READ.getStatus());
        notificationMapper.updateByPrimaryKey(notification);
        NotificationDTO notificationDTO = new NotificationDTO();
        BeanUtils.copyProperties(notification,notificationDTO);
        notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));

        return notificationDTO;

    }


}



