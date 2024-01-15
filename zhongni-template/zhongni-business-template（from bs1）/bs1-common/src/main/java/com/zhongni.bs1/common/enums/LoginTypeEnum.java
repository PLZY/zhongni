package com.zhongni.bs1.common.enums;

import com.zhongni.bs1.common.exception.BusinessException;

public enum LoginTypeEnum {

    LOCAL("LOCAL","本网站登录", "localLoginServiceImpl"),

    THIRD("THIRD", "三方登录", "thirdLoginServiceImpl");

    private String code;
    private String msg;
    private String implClass;

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
    public String getImplClass() {
        return implClass;
    }



    LoginTypeEnum(String code, String msg, String implClass) {
        this.code = code;
        this.msg = msg;
        this.implClass = implClass;
    }

    public static String getImplClass(String loginType)
    {
        for (LoginTypeEnum login : LoginTypeEnum.values())
        {
            if (loginType.equals(login.getCode()))
            {
                return login.implClass;
            }
        }

        throw new BusinessException(BusinessExceptionEnum.PARAMS_EXCEPTION_ILLEGAL_LOGIN_TYPE, loginType);
    }
}
