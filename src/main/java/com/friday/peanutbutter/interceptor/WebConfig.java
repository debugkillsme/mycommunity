package com.friday.peanutbutter.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
//注释掉该注解，避免默认加载静态资源的路径导致css样式被拦截
//@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    SessionInterceptor sessionInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //参数表示要经过处理的目录
        registry.addInterceptor(sessionInterceptor).addPathPatterns("/**");
    }
}