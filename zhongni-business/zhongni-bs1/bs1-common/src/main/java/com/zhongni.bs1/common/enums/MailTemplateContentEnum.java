package com.zhongni.bs1.common.enums;

public enum MailTemplateContentEnum {

    REGISTER("REGISTER","注册邮件"),

    LOGIN("LOGIN","登录邮件"),

    RESET("RESET", "重置密码邮件");

    private String code;
    private String msg;

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }



    MailTemplateContentEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
