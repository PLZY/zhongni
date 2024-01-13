package com.zhongni.bs1.common.enums;

public enum UserStatusEnum {


    CREATING("CREATING","创建中"),
    ENABLE("ENABLE","有效"),

    DISABLE("DISABLE", "无效");

    private String code;
    private String msg;

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }



    UserStatusEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
