package com.zhongni.bs1.common.enums;

public enum UserTypeEnum {


    LOCAL("LOCAL","本地注册用户"),

    THIRD("THIRD","三方对接用户");

    private String code;
    private String msg;

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }



    UserTypeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
