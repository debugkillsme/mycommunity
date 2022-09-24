package com.friday.peanutbutter.controller;

import com.friday.peanutbutter.dto.NotificationDTO;
import com.friday.peanutbutter.enums.NotificationTypeEnum;
import com.friday.peanutbutter.model.User;
import com.friday.peanutbutter.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

@Controller
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/notification/{id}")
    public String profile(HttpServletRequest request, @PathVariable(name = "id") Long id) {
        User user = (User)request.getSession().getAttribute("user");
        if(user==null){
            //会话中user为空直接跳转
            //这里可以设置一些错误提示
            return "redirect:/";
        }

        NotificationDTO notificationDTO = notificationService.read(id,user);

        if(NotificationTypeEnum.REPLY_COMMENT.getType()== notificationDTO.getType()||
        NotificationTypeEnum.REPLY_POST.getType() == notificationDTO.getType()){
            return "redirect:/thread/"+notificationDTO.getOuterId();
        }else {
            return "redierct:/";

        }

    }
}
