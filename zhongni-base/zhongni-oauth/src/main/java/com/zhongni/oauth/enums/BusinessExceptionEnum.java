package com.zhongni.oauth.enums;

/**
 *
 * 错误码含义
 * 前三位代表系统模块，如100是oauth系统 101是gateway系统以此类推
 * 第四位代表异常信息种类如 如1-参数异常 2-远程调用异常 3-系统异常...
 * 后几位是流水号，依次递增即可
 */
public enum BusinessExceptionEnum {
    UNKNOWN_EXCEPTION("-1","未知的异常，请查看日志"),
    CHECK_SIGN_FAIL("100-10001","验签失败，无法解析签名"),

    REMOTE_REQUEST_FAIL("100-20001","远程调用失败"),

    UN_RIGHT_NAME_AND_PASSWORD("100-30001","系统异常-用户名密码不正确"),
    USER_EXCEPTION_ILLEGAL_LOGIN_INFO("100-40001", "用户异常-无法获取合法的登录信息，请确认当前用户是否已经登录"),
    USER_LOGIN_TIME_OUT("100-40002", "用户异常-登录信息已经失效"),
    ;


    private String code;
    private String msg;

    public String getCode() {
        return code;
    }
    public String getMsg() {
        return msg;
    }


    BusinessExceptionEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
