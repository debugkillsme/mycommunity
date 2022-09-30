package com.friday.peanutbutter.controller;


import com.friday.peanutbutter.dto.NotificationDTO;
import com.friday.peanutbutter.dto.ThreadDTO;
import com.friday.peanutbutter.model.User;
import com.friday.peanutbutter.service.NotificationService;
import com.friday.peanutbutter.service.ThreadService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {
    @Autowired
    private ThreadService threadService;

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/profile/{action}")
    public String profile(HttpServletRequest request,
                          @PathVariable(name = "action") String action,
                          @RequestParam(name ="page",defaultValue = "1")Integer page,
                          @RequestParam(name = "size",defaultValue = "2")Integer size,
                          Model model) {

        User user = (User)request.getSession().getAttribute("user");
        if(user==null){
            //会话中user为空直接跳转
            //这里可以设置一些错误提示
            return "redirect:/";
        }
        if ("threads".equals(action)) {
            //前端路由选择,search内容为空
            PageInfo<ThreadDTO> pagination= threadService.list(user.getId(), page,size);
            model.addAttribute("section", "threads");
            model.addAttribute("sectionName", "我的帖子");
            model.addAttribute("pagination",pagination);
        } else if("replies".equals(action)){
            PageInfo<NotificationDTO> pagination = notificationService.list(user.getId(), page,size);
            //获得未读消息数
            model.addAttribute("section","replies");
            model.addAttribute("sectionName","最新回复");
            model.addAttribute("pagination",pagination);


        }

        return "profile";
    }

}
