package com.zhongni.bs1.common.resp.nodejs;

import com.zhongni.bs1.common.constants.BusinessConstants;
import lombok.Data;

import java.io.Serializable;

/**
 * 通用出参对象
 * data对象中放置具体的数据
 */
@Data
public class NodeCommonResponse<T> implements Serializable {
    // "响应码",required = true,example = "000")
    private String code;

    // 错误信息（响应码不为0时需放置，为0时可不放）
    private String message;

    // "返回的数据",required = true,example = "")
    private T data;


    protected NodeCommonResponse(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static NodeCommonResponse<String> success(){
        return new NodeCommonResponse<>(BusinessConstants.NODE_DEFAULT_SUCCESS_CODE, "", "");
    }

    public static <T> NodeCommonResponse<T> success(T data){
        return new NodeCommonResponse<>(BusinessConstants.NODE_DEFAULT_SUCCESS_CODE, "", data);
    }

    public static NodeCommonResponse<String> failure(){
        return new NodeCommonResponse<>(BusinessConstants.DEFAULT_ERROR_CODE, BusinessConstants.DEFAULT_FAIL_MSG, "");
    }

    public static <T> NodeCommonResponse<T> failure(T data){
        return new NodeCommonResponse<>(BusinessConstants.DEFAULT_ERROR_CODE, BusinessConstants.DEFAULT_FAIL_MSG, data);
    }

    public static NodeCommonResponse<String> failure(String errMsg){
        return new NodeCommonResponse<>(BusinessConstants.DEFAULT_ERROR_CODE, errMsg, "");
    }

    public static <T> NodeCommonResponse<T> resp(String code, String errMsg, T data){
        return new NodeCommonResponse<>(code, errMsg, data);
    }
}
