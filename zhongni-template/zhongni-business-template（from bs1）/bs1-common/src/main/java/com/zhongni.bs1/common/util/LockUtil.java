package com.zhongni.bs1.common.util;

import com.zhongni.bs1.common.constants.BusinessConstants;
import com.zhongni.bs1.common.constants.CacheKeyConstants;
import com.zhongni.bs1.common.enums.LockTypeEnum;
import com.zhongni.bs1.common.lock.Locked;
import com.zhongni.bs1.common.spring.AppContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Consumer;
import java.util.function.Function;

@Slf4j
public class LockUtil {
    private static final String DATE_FORMATTER = "yyyy-MM-dd HH:mm:ss SSS";
    private static final Locked LOCKED = AppContext.getBean(LockTypeEnum.getImplClass(
            AppContext.getApplicationContext().getEnvironment().getProperty(BusinessConstants.DEPLOYMENT_MODE)), Locked.class);

    private LockUtil(){}

    public static<T, R> R syncExecute(String key, T inParams, Function<T, R> function)
    {
        if(StringUtils.isBlank(key))
        {
            throw new NullPointerException();
        }

        String realLockKey = CacheKeyConstants.CACHE_LOCK_PREFIX + key;
        try
        {
            log.info("syncExecute will lock by this key[{}], current time is {}",
                    realLockKey, LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_FORMATTER)));
            LOCKED.lock(realLockKey);
            log.info("syncExecute locked by this key[{}] success, current time is {}, current Thread is {}",
                    realLockKey, LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_FORMATTER)), Thread.currentThread().getId());
            return function.apply(inParams);
        }
        finally
        {
            LOCKED.unlock(realLockKey);
            log.info("syncExecute unlock by this key[{}] complete, current time is {}, current thread is {}",
                    realLockKey, LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_FORMATTER)), Thread.currentThread().getId());
        }
    }

    public static<T> void syncExecute(String key, T inParams, Consumer<T> consumer)
    {
        if(StringUtils.isBlank(key))
        {
            throw new NullPointerException();
        }

        String realLockKey = CacheKeyConstants.CACHE_LOCK_PREFIX + key;
        try
        {
            log.info("syncExecute no return will lock by this key[{}], current time is {}",
                    realLockKey, LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_FORMATTER)));
            LOCKED.lock(realLockKey);
            log.info("syncExecute no return locked by this key[{}] success, current time is {}",
                    realLockKey, LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_FORMATTER)));
            consumer.accept(inParams);
        }
        finally
        {
            LOCKED.unlock(realLockKey);
            log.info("syncExecute no return unlock by this key[{}] complete, current time is {}",
                    realLockKey, LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_FORMATTER)));
        }
    }

    public static<T> void executeIfGetLock(String key, T inParams, Consumer<T> consumer)
    {
        if(StringUtils.isBlank(key))
        {
            throw new NullPointerException("");
        }

        String realLockKey = CacheKeyConstants.CACHE_LOCK_PREFIX + key;
        if(!LOCKED.tryLock(realLockKey))
        {
            log.debug("scramble lock by this key[{}] fail, not execute...", realLockKey);
            return;
        }

        try
        {
            log.info("executeIfGetLock locked by this key[{}] success, current time is {}",
                    realLockKey, LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_FORMATTER)));
            consumer.accept(inParams);
        }
        finally
        {
            LOCKED.unlock(realLockKey);
            log.info("executeIfGetLock unlock by this key[{}] complete, current time is {}",
                    realLockKey, LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_FORMATTER)));
        }
    }
}
