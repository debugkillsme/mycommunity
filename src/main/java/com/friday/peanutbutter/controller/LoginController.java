package com.friday.peanutbutter.controller;

import com.friday.peanutbutter.model.User;
import com.friday.peanutbutter.service.MailService;
import com.friday.peanutbutter.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.UUID;
import java.util.regex.Pattern;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private MailService mailService;

    @GetMapping("/login")
    public String tryLog() {
        return "login";
    }

    @PostMapping("/sendCode")
    public String sendCode(@RequestParam("email")String email, HttpSession session, Model model) {
        if (StringUtils.isBlank(email)) {
            model.addAttribute("error", "请输入邮箱");
        } else if (!Pattern.matches("^(\\w+([-.][A-Za-z0-9]+)*){3,18}@\\w+([-.][A-Za-z0-9]+)*\\.\\w+([-.][A-Za-z0-9]+)*$", email)) {
            model.addAttribute("error", "输入邮箱格式不正确");
        } else {
            //将email和checkCode添加到会话中去
            if(mailService.sendMimeMail(email, session)) {
                model.addAttribute("message", "验证码发送成功");
            }else{
                model.addAttribute("error","验证码发送失败");
            }
            model.addAttribute("email",email);
        }

        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam(value = "code") String code,
                        HttpSession session, HttpServletResponse response,Model model) {

        String codeInSession = session.getAttribute("checkCode").toString();

        String email = session.getAttribute("email").toString();

        //进行验证码的比对(页面提交的验证码和Session中保存的验证码比对)
        if (!StringUtils.isBlank(codeInSession) && codeInSession.equals(code)) {
            //如果能够比对成功,说明登录成功
            User user = new User();
            //登录生成token(16位数字组成的唯一ID),绑定User识别是否已经存在
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setName("jasen");
            //一律设置为小猫头像
            user.setAvatarUrl("/images/cat.png");
            //设置登录用户类型
            user.setAccountType("email");
            //如果当前登录的用户的id等于user的accountId,则到数据库中更新user的token
            user.setAccountId(email);
            //每次登录会插入重复的user，需要优化,选择插入或更新
            userService.createOrUpdate(user);
            //将token放到cookie中,再把cookie写到response中
            response.addCookie(new Cookie("token", token));
        }else{
            model.addAttribute("error","验证码输入错误");
            model.addAttribute("email",email);
        }

        return "redirect:/";
    }

}
