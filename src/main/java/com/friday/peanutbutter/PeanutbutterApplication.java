package com.friday.peanutbutter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PeanutbutterApplication {
    //带有注解的文件只要在Application同一级及以下都会加载

    public static void main(String[] args) {
        SpringApplication.run(PeanutbutterApplication.class, args);
    }

}
