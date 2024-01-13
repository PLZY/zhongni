package com.zhongni.bs1.common.enums;

public enum EventDataTypeEnum {
    YELLOW_CARD("YELLOW_CARD","黄牌数"),
    RED_CARD("RED_CARD", "红牌数"),
    CORNER("CORNER", "角球数"),
    GOAL("GOAL", "进球数");

    private final String dataType;
    private final String dataTypeDesc;

    public String getDataType() {
        return dataType;
    }

    public String getDataTypeDesc() {
        return dataTypeDesc;
    }

    EventDataTypeEnum(String dataType, String dataTypeDesc) {
        this.dataType = dataType;
        this.dataTypeDesc = dataTypeDesc;
    }
}
