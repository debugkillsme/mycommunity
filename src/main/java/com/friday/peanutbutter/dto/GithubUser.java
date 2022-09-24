package com.friday.peanutbutter.dto;

import lombok.Data;

//使用GithubUser账户存储用户信息
@Data
public class GithubUser {
    private String name;
    private Long id;
    private String bio;
    private String avatarUrl;

    @Override
    public String toString() {
        return "GithubUser{" +
                "name='" + name + '\'' +
                '}';
    }
}

