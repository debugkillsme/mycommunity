package com.friday.peanutbutter.controller;

import com.friday.peanutbutter.dto.AccessTokenDTO;
import com.friday.peanutbutter.dto.GithubUser;
import com.friday.peanutbutter.model.User;
import com.friday.peanutbutter.provider.GithubProvider;
import com.friday.peanutbutter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class AuthorizeController {
    //调用provider方法
    @Autowired
    //自动加载Spring容器中的实例
    private GithubProvider githubProvider;

    @Value("${github.client.id}")
    private  String clientId;
    @Value("${github.client.secret}")
    private  String clientSecret;

    //这个redirect.uri是自己在app上设定的跳转地址
    @Value("${github.redirect.uri}")
    private String redirectUri;

    @Autowired
    private UserService userService;

    @GetMapping("/callback")
    //@RequestParam将请求参数绑定到控制器的方法上
    //解析出code,获得accessToken
    //Spring自动把上下文中的request放到参数中
    public String callback(@RequestParam(name="code") String code,
                           @RequestParam(name="state") String state,
                           HttpServletResponse response){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setState(state);
        //accessTokenDTO携带了code,去请求获得accessToken
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser = githubProvider.getUser(accessToken);
        //加上且内容不为空判断(因为githubUser是解析而来可能为空)
        if(githubUser!=null&&githubUser.getId()!=null){
            //返回不为空写session和cookie
            User user = new User();
            //登录生成token(16位数字组成的唯一ID),绑定User识别是否已经存在
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            //设置登录用户类型
            user.setAccountType("github");
            user.setName(githubUser.getName());
            //如果当前登录的用户的id等于user的accountId,则到数据库中更新user的token
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setAvatarUrl(githubUser.getAvatarUrl());
            //每次登录会插入重复的user，需要优化,选择插入或更新
            userService.createOrUpdate(user);

            //将token放到cookie中,再把cookie写到response中
            response.addCookie(new Cookie("token",token));
        }
        //重定向回到初始页面
        return "redirect:/";
    }


    @GetMapping("/logout")
    public String logout(HttpServletRequest request,HttpServletResponse response){
        //将会话中的user删除
        request.getSession().removeAttribute("user");
        //用null值覆盖的方式删除cookie中的token,cookie在客户端，需要在response中进行操作
        Cookie cookie = new Cookie("token",null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/";
    }
}
