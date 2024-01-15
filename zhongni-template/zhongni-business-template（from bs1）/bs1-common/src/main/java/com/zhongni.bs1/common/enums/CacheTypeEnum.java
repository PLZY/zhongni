package com.zhongni.bs1.common.enums;

import com.zhongni.bs1.common.exception.BusinessException;

public enum CacheTypeEnum {
    SINGLE("single","caffeineCacheServiceImpl"),

    CLUSTER("cluster", "redisCacheServiceImpl");

    private final String cacheType;
    private final String implClass;

    public String getCacheType() {
        return cacheType;
    }

    public String getImplClass() {
        return implClass;
    }

    CacheTypeEnum(String cacheType, String implClass) {
        this.cacheType = cacheType;
        this.implClass = implClass;
    }

    public static String getImplClass(String cacheTypeName)
    {
        for (CacheTypeEnum cache : CacheTypeEnum.values())
        {
            if (cacheTypeName.equals(cache.getCacheType()))
            {
                return cache.implClass;
            }
        }

        throw new BusinessException(BusinessExceptionEnum.SYSTEM_EXCEPTION_UNKNOWN_CACHE_TYPE, cacheTypeName);
    }
}
