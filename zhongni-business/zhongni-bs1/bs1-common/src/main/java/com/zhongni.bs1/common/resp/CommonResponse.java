package com.zhongni.bs1.common.resp;

import com.zhongni.bs1.common.constants.BusinessConstants;
import lombok.Data;

import java.io.Serializable;

/**
 * 通用出参对象
 * data对象中放置具体的数据
 */
@Data
public class CommonResponse<T> implements Serializable {
    // "响应码",required = true,example = "000")
    private String code;

    // 错误信息（响应码不为0时需放置，为0时可不放）
    private String errMsg;

    // "返回的数据",required = true,example = "")
    private T data;


    protected CommonResponse(String code, String errMsg, T data) {
        this.code = code;
        this.errMsg = errMsg;
        this.data = data;
    }

    public static CommonResponse<String> success(){
        return new CommonResponse<>(BusinessConstants.DEFAULT_SUCCESS_CODE, "", "");
    }

    public static <T> CommonResponse<T> success(T data){
        return new CommonResponse<>(BusinessConstants.DEFAULT_SUCCESS_CODE, "", data);
    }

    public static CommonResponse<String> failure(){
        return new CommonResponse<>(BusinessConstants.DEFAULT_ERROR_CODE, BusinessConstants.DEFAULT_FAIL_MSG, "");
    }

    public static <T> CommonResponse<T> failure(T data){
        return new CommonResponse<>(BusinessConstants.DEFAULT_ERROR_CODE, BusinessConstants.DEFAULT_FAIL_MSG, data);
    }

    public static CommonResponse<String> failure(String errMsg){
        return new CommonResponse<>(BusinessConstants.DEFAULT_ERROR_CODE, errMsg, "");
    }

    public static <T> CommonResponse<T> resp(String code, String errMsg, T data){
        return new CommonResponse<>(code, errMsg, data);
    }
}
