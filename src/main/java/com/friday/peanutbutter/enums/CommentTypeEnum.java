package com.friday.peanutbutter.enums;

public enum CommentTypeEnum {
    //枚举设定回复类型
    POST_THREAD(1),
    COMMENT(2);
    private Integer type;

    public Integer getType() {
        return type;
    }

    CommentTypeEnum(Integer type) {
        this.type = type;
    }

    public static boolean isExist(Integer type) {
        for(CommentTypeEnum commentTypeEnum:CommentTypeEnum.values()){
            if(commentTypeEnum.getType() == type){
                return true;
            }

        }
        return false;
    }
}
