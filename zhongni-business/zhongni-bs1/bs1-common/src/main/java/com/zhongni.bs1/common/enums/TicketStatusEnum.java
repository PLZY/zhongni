package com.zhongni.bs1.common.enums;

public enum TicketStatusEnum {

    UNOPEN("UNOPEN","未开"),
    OPENING("OPENING","开采中"),
    OPENED("OPENED", "已开"),
    CASHED("CASHED","已兑奖");

    private String code;
    private String msg;

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }



    TicketStatusEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
