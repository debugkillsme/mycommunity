package com.friday.peanutbutter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//除了使用mapper注解，springboot扫描mybatis生成的mapper使用@MapperScan注解,并在application.properties中设置路径
@MapperScan("com.friday.peanutbutter.mapper")
public class PeanutbutterApplication {
    //带有注解的文件只要在Application同一级及以下都会加载

    public static void main(String[] args) {
        SpringApplication.run(PeanutbutterApplication.class, args);
    }

}
