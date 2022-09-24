package com.friday.peanutbutter.dto;

import com.friday.peanutbutter.model.User;
import lombok.Data;

@Data
public class ThreadDTO {
    private Long id;
    private String title;
    private String description;
    private Long gmtCreate;
    private Long gmtModified;
    private String tag;
    private Long creator;
    private Integer viewCount;
    private Integer commentCount;
    private Integer likeCount;
    //多了一个user对象，帖子关联user获取头像
    private User user;
}
