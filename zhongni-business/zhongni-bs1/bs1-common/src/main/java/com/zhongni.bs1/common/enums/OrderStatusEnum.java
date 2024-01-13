package com.zhongni.bs1.common.enums;

public enum OrderStatusEnum {

    DEALING("DEALING","处理中"),

    COMPLETE("COMPLETE", "已完成");

    private String code;
    private String msg;

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }



    OrderStatusEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
