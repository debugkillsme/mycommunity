package com.friday.peanutbutter.provider;

import com.alibaba.fastjson.JSON;
import com.friday.peanutbutter.dto.AccessTokenDTO;
import com.friday.peanutbutter.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

//使用不同的包隔离业务
//Component注解:通过类路径扫描自动侦测以及自动装配到Spring容器中(IOC)
@Component
public class GithubProvider {
    //调用地址获取access_token
    public String getAccessToken(AccessTokenDTO accessTokenDTO){
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(mediaType,JSON.toJSONString(accessTokenDTO));//第一个参数是string,第二个参数是string类型的JSON
        //建立一个request，根据client的newCall执行获得对应的JSON
        //携带code调用access_Token接口,进行request请求
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try {
            //从response中获得accessToken
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            //这里打印出accessToken
            String token = string.split("&")[0].split("=")[1];
            return token;
        }catch (IOException e) {
            //whatever
            return null;
        }
    }

    public GithubUser getUser(String accessToken){
        //使用accessToken获得githubUser的信息
        OkHttpClient client = new OkHttpClient();

        //根据得到的accessToken再次做一个请求
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token="+accessToken)
                .header("Authorization","token "+accessToken)
                .build();

        try {
            //响应中携带了user信息
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            //拿到的JSON中包括name,id,bio等字符串信息
            //用JSON.parse自动地将String解析为GithubUser的类对象,JSON中的avatar_uri可以自动解析成GithubUser类中的驼峰写法
            GithubUser githubUser = JSON.parseObject(string,GithubUser.class);
            return githubUser;

        }catch (IOException e){
            System.out.println("we got no github user!");
            //whatever
        }
        return null;
    }
}
