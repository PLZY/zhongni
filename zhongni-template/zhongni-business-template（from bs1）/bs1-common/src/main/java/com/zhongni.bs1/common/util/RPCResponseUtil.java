package com.zhongni.bs1.common.util;

import com.zhongni.bs1.common.constants.BusinessConstants;
import com.zhongni.bs1.common.enums.BusinessExceptionEnum;
import com.zhongni.bs1.common.exception.BusinessException;
import com.zhongni.bs1.common.resp.CommonResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RPCResponseUtil {
    private static final Logger logger = LoggerFactory.getLogger(RPCResponseUtil.class);

    private RPCResponseUtil(){}
    public static <T> T parseData(CommonResponse<T> commonResponse)
    {
        if(!BusinessConstants.DEFAULT_SUCCESS_CODE.equals(commonResponse.getCode())){
            throw new BusinessException(BusinessExceptionEnum.REMOTE_EXCEPTION, commonResponse.getErrMsg());
        }

        return commonResponse.getData();
    }

    public static <T> T parseDataNoThrow(CommonResponse<T> commonResponse)
    {
        try
        {
            return parseData(commonResponse);
        }
        catch (BusinessException e)
        {
            // 远程调用失败只记录一下返回的错误信息，不抛异常，直接返回null
            logger.error("RPCResponse is FAIL, reason is : {}", e.getMessage());
            return null;
        }
    }
}
