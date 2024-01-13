package com.zhongni.bs1.common.exception;


import com.zhongni.bs1.common.enums.BusinessExceptionEnum;
import lombok.Data;
import org.springframework.util.StringUtils;

@Data
public class BusinessException extends RuntimeException{
    private final String errorCode;

    // 额外的信息描述
    /**
     * 自己项目内部的异常信息通过BusinessExceptionEnum枚举类将错误信息统一管理，并把描述信息放入detailMessage变量中
     * extDesc 主要是用于放置调用三方程序返回的错误信息。
     */
    private String extDesc;

    public BusinessException(BusinessExceptionEnum businessExceptionEnum) {
        super(businessExceptionEnum.getMsgEn());
        this.errorCode = businessExceptionEnum.getCode();
    }

    public BusinessException(BusinessExceptionEnum businessExceptionEnum, String extDesc) {
        super(businessExceptionEnum.getMsgEn());
        this.errorCode = businessExceptionEnum.getCode();
        this.extDesc = extDesc;
    }

    @Override
    public String getMessage() {
        StringBuilder message = new StringBuilder("'").append(super.getMessage()).append("'");
        if(!StringUtils.isEmpty(extDesc))
        {
            message.append(", ext msg : [").append(extDesc).append("]");
        }

        return message.toString();
    }
}
