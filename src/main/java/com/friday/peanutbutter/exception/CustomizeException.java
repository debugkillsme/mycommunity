package com.friday.peanutbutter.exception;

public class CustomizeException extends RuntimeException{
    private String message;
    private Integer code;
    public CustomizeException(ICustomizeErrorCode errorCode){
        this.message = errorCode.getMessage();
        this.code = errorCode.getCode();
    }

    @Override
    public String getMessage() {
        return message;
    }

    //runtime父类中没有这个方法，无需override
    public Integer getCode() {
        return code;
    }
}
