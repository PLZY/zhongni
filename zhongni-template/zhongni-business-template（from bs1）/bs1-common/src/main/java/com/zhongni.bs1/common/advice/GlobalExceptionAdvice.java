package com.zhongni.bs1.common.advice;

import com.zhongni.bs1.common.constants.BusinessConstants;
import com.zhongni.bs1.common.exception.BusinessException;
import com.zhongni.bs1.common.resp.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
@Slf4j
public class GlobalExceptionAdvice {
    @Value("${spring.application.name}")
    private String applicationName;

    @ExceptionHandler(BusinessException.class)
    public CommonResponse<String> handleBusinessException(BusinessException businessException){
        log.error("BusinessException is {} ", businessException.getMessage(), businessException);
        return CommonResponse.resp(businessException.getErrorCode(), businessException.getMessage(), "");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public CommonResponse<String> methodArgumentNotValidException(MethodArgumentNotValidException methodArgumentNotValidException){
        StringBuilder sb = new StringBuilder("[");
        for (ObjectError objectError : methodArgumentNotValidException.getBindingResult().getAllErrors()) {
            sb.append(objectError.getDefaultMessage()).append(",");
        }

        sb.append("]");
        return CommonResponse.resp(BusinessConstants.DEFAULT_ERROR_CODE, sb.toString(), "");
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public CommonResponse<String> handleHttpRequestMethodNotSupportedException(Exception exception){
        log.error("HttpRequestMethodNotSupportedException is {} ", exception.getMessage(), exception);
        return CommonResponse.resp(BusinessConstants.DEFAULT_ERROR_CODE, exception.getMessage(), "");
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public CommonResponse<String> handleHttpMediaTypeNotSupportedException(Exception exception){
        log.error("HttpMediaTypeNotSupportedException is {} ", exception.getMessage(), exception);
        return CommonResponse.resp(BusinessConstants.DEFAULT_ERROR_CODE, exception.getMessage(), "");
    }

    @ExceptionHandler(Exception.class)
    public CommonResponse<String> handleException(Exception exception){
        log.error("Exception is {} ", exception.getMessage(), exception);
        return CommonResponse.resp(BusinessConstants.DEFAULT_ERROR_CODE, "project[" + applicationName + "] has an unexpected Exception occurred, contact the Administrator please.", "");
    }

    @ExceptionHandler(Throwable.class)
    public CommonResponse<String> handleError(Exception exception){
        log.error("Error is {} ", exception.getMessage(), exception);
        return CommonResponse.resp(BusinessConstants.DEFAULT_ERROR_CODE, "project[" + applicationName + "] has an unexpected Error occurred, contact the Administrator please.", "");
    }
}
