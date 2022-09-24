package com.friday.peanutbutter.service;

import com.friday.peanutbutter.dto.NotificationDTO;
import com.friday.peanutbutter.dto.PaginationDTO;
import com.friday.peanutbutter.enums.NotificationStatusEnum;
import com.friday.peanutbutter.enums.NotificationTypeEnum;
import com.friday.peanutbutter.exception.CustomizeErrorCode;
import com.friday.peanutbutter.exception.CustomizeException;
import com.friday.peanutbutter.mapper.NotificationMapper;
import com.friday.peanutbutter.model.Notification;
import com.friday.peanutbutter.model.NotificationExample;
import com.friday.peanutbutter.model.User;
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

    public PaginationDTO list(Long userId, Integer page, Integer size) {
        PaginationDTO<NotificationDTO> paginationDTO = new PaginationDTO();
        //Integer totalCount = threadMapper.countByUserId(userId);
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria().
                andReceiverEqualTo(userId);
        //按照降序排列将未读消息放在上面
        notificationExample.setOrderByClause("gmt_create desc");
        Integer totalCount = (int) notificationMapper.countByExample(notificationExample);
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
        //不同页数情况参数的设定
        Integer offset = size*(page-1);
        List<Notification> notifications = notificationMapper.selectByExampleWithRowbounds(notificationExample,new RowBounds(offset,size));
        if(notifications.size()==0){
            return paginationDTO;
        }

        List<NotificationDTO> notificationDTOS = new ArrayList<>();
        for(Notification notification:notifications){
            NotificationDTO notificationDTO = new NotificationDTO();
            BeanUtils.copyProperties(notification,notificationDTO);
            notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
            notificationDTOS.add(notificationDTO);
        }
        paginationDTO.setData(notificationDTOS);
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



