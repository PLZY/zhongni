package com.zhongni.bs1.common.enums;

public enum TicketIndexTypeEnum {

    IMAGE("IMAGE","图片"),
    BONUS("BONUS","奖金");

    private String code;
    private String msg;

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }



    TicketIndexTypeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
