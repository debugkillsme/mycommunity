package com.friday.peanutbutter.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode{

    //2XXX表示系统错误
    THREAD_NOT_FOUND(2001,"你找的帖子不在了，要不换个试试"),
    TARGET_PARAM_NOT_FOUND(2002,"未选中任何帖子或评论进行回复"),
    NO_LOGIN(2003,"未登录不能进行评论"),
    SYS_ERROR(2004,"服务器冒烟了"),
    TYPE_PARAM_WRONG(2005,"评论类型不存在"),
    COMMENT_NOT_FOUND(2006,"评论不存在"),
    CONTENT_IS_EMPTY(2007,"输入内容不能为空"),
    NOTIFICATION_NOT_FOUND(2008,"通知没找到"),
    READ_NOTIFICATION_FAILED(2009,"兄弟走到别人的通知里了!"),
    FILE_UPLOAD_FAILED(2010,"获取上传图片失败");
    private Integer code;
    private String message;

    CustomizeErrorCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getMessage(){
        return message;

    }

    @Override
    public Integer getCode(){
        return code;
    }

}
