package com.zhongni.bs1.service.service.lock.impl;

import com.zhongni.bs1.common.enums.BusinessExceptionEnum;
import com.zhongni.bs1.common.exception.BusinessException;
import com.zhongni.bs1.common.lock.Locked;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service("redisLockImpl")
public class RedisLockedImpl implements Locked {

    /**
     * 上锁时间 单位（秒）
     */
    private static final long DEFAULT_LOCK_TIMEOUT = 60;

    /**
     * 默认的等待超时时间 单位（秒）
     */
    private static final long DEFAULT_WAIT_TIMEOUT = 120;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public void lock(String key) {
        long currentTimeMillis = System.currentTimeMillis();
        for(;;)
        {
            // 加锁成功直接返回
            if(tryLock(key))
            {
                break;
            }

            // 等待超时直接报错
            if((currentTimeMillis + DEFAULT_WAIT_TIMEOUT * 1000) < System.currentTimeMillis())
            {
                log.warn("wait for redis lock by this key[{}], wait time out", key);
                throw new BusinessException(BusinessExceptionEnum.SYSTEM_EXCEPTION_WAIT_LOCK_TIMEOUT);
            }

            // 未争抢到锁则稍等一会再重试
            try
            {
                Thread.sleep(20);
            }
            catch (InterruptedException e)
            {
                log.error("RedisLocked lock Thread.sleep is InterruptedException {}", e.getMessage(), e);
                throw new BusinessException(BusinessExceptionEnum.UNKNOWN_EXCEPTION);
            }
        }

    }

    @Override
    public void unlock(String key)
    {
        Object value = redisTemplate.opsForValue().get(key);
        if(null == value)
        {
            // 如果是空证明当前key对应的锁已经不存在了，不需要处理
            log.info("redisTemplate.get lock is null, need not unlock, key is {}", key);
            return;
        }

        long threadId = Long.parseLong(value.toString());
        // 如果加锁的是当前线程则允许解锁
        if(Thread.currentThread().getId() == threadId)
        {
            redisTemplate.delete(key);
        }
    }

    @Override
    public boolean tryLock(String key) {
        return Boolean.TRUE.equals(redisTemplate.opsForValue().
                setIfAbsent(key, Thread.currentThread().getId(), DEFAULT_LOCK_TIMEOUT , TimeUnit.SECONDS));
    }
}
