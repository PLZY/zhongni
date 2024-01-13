package com.zhongni.bs1.common.enums;

import com.zhongni.bs1.common.exception.BusinessException;

public enum LockTypeEnum {
    SINGLE("single","singleLockImpl"),

    CLUSTER("cluster", "redisLockImpl");

    private final String lockType;
    private final String implClass;

    public String getLockType() {
        return lockType;
    }

    public String getImplClass() {
        return implClass;
    }

    LockTypeEnum(String lockType, String implClass) {
        this.lockType = lockType;
        this.implClass = implClass;
    }

    public static String getImplClass(String lockTypeName)
    {
        for (LockTypeEnum lock : LockTypeEnum.values())
        {
            if (lockTypeName.equals(lock.getLockType()))
            {
                return lock.implClass;
            }
        }

        throw new BusinessException(BusinessExceptionEnum.SYSTEM_EXCEPTION_UNKNOWN_LOCK_TYPE, lockTypeName);
    }
}
