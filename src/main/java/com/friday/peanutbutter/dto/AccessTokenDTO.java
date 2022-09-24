package com.friday.peanutbutter.dto;

import lombok.Data;

//DTO:数据传输对象
@Data
public class AccessTokenDTO {
    private String client_id;
    private String redirect_uri;
    private String client_secret;
    private String code;
    private String state;
}
