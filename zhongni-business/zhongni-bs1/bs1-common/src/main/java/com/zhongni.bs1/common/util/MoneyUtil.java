package com.zhongni.bs1.common.util;

import com.zhongni.bs1.common.enums.BusinessExceptionEnum;
import com.zhongni.bs1.common.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

@Slf4j
public class MoneyUtil {
    /**
     * 金额为分的格式
     */
    public static final String CURRENCY_FEN_REGEX = "-?[0-9]+";

    public static String longFen2Yuan(Long amount) {
        return fen2Yuan(String.valueOf(amount));
    }

    /**
     * 将分为单位的转换为元 （除100）
     */
    public static String fen2Yuan(String amount) {
        if(StringUtils.isBlank(amount) || "null".equals(amount)){
            log.warn("inParams amount in fen2YuanStr is null, set default value 0.00");
            return "0.00";
        }

        if (!amount.matches(CURRENCY_FEN_REGEX)) {
            throw new BusinessException(BusinessExceptionEnum.PARAMS_EXCEPTION_INAVLID_MONEY_NUMBER_PATTERN, amount);
        }

        String yuanStr = formatFen(BigDecimal.valueOf(Long.parseLong(amount)).divide(BigDecimal.valueOf(100)));
        log.info("{}￠ -------> {}$", amount, yuanStr);
        return yuanStr;
    }

    /**
     * 格式化数字
     */
    private static String formatFen(BigDecimal fen){
        // 格式化保留两位小数
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        return decimalFormat.format(fen);
    }

    /**
     * 将元为单位的参数转换为分 , 只对小数点前2位支持
     */
    public static String yuan2Fen(String yuan){
        if(StringUtils.isBlank(yuan) || "null".equals(yuan)){
            log.warn("inParams amount in yuan2FenInt is null, set default value 0");
            return "0";
        }

        BigDecimal fenBd = new BigDecimal(yuan).multiply(BigDecimal.valueOf(100));
        fenBd = fenBd.setScale(0, RoundingMode.HALF_UP);
        String strFen = String.valueOf(fenBd.intValue());
        log.info("{}$ -------> {}￠", yuan, strFen);
        return strFen;
    }

    public static Long yuan2LongFen(String yuan){
        String fenStr = yuan2Fen(yuan);
        if (!fenStr.matches(CURRENCY_FEN_REGEX)) {
            throw new BusinessException(BusinessExceptionEnum.PARAMS_EXCEPTION_INAVLID_MONEY_NUMBER_PATTERN, fenStr);
        }

        return Long.valueOf(fenStr);
    }
}
