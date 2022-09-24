package com.friday.peanutbutter.dto;

import lombok.Data;

@Data
public class ThreadQueryDTO {
    private String search;
    private Integer page;
    private Integer size;
}
