package com.zhongni.bs1.service.service.lock.impl;

import com.alibaba.fastjson.JSON;
import com.zhongni.bs1.common.lock.Locked;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
@Service("singleLockImpl")
public class SingleLockedImpl implements Locked {
    private static final Map<String, ReentrantLock> lockMap = new ConcurrentHashMap<>();
    private static final Map<String, LocalDateTime> localDateTimeMap = new ConcurrentHashMap<>();

    @Override
    public void lock(String key)
    {
        // 每次上锁前先清除一下无效的数据
        clearOldDataFromMap();
        localDateTimeMap.put(key, LocalDateTime.now());
        // 从目前测试来看，putIfAbsent是线程安全的，只会有一个线程赋值成功
        ReentrantLock lock = lockMap.putIfAbsent(key, new ReentrantLock());
        // 如果是null, 说明是第一次放入，重新取一次值即可
        if(null == lock)
        {
            lock = lockMap.get(key);
        }

        lock.lock();
        log.info("current thread is {}, map is {}", Thread.currentThread().getId(), JSON.toJSONString(lockMap));
    }

    @Override
    public void unlock(String key)
    {
        ReentrantLock lock = lockMap.get(key);
        if(null == lock)
        {
            // 如果是空证明当前key对应的锁已经不存在了，不需要处理
            log.info("lockMap.remove is null, need not unlock, key is {}, current thread is {}", key, Thread.currentThread().getId());
            return;
        }

        log.info("key is {}, current thread is {} will unlock", key, Thread.currentThread().getId());
        lock.unlock();
    }

    @Override
    public boolean tryLock(String key) {
        // 从目前测试来看，putIfAbsent是线程安全的，只会有一个线程赋值成功
        Lock lock = lockMap.putIfAbsent(key, new ReentrantLock());
        // 如果是null, 说明是第一次放入，重新取一次值即可
        if(null == lock)
        {
            return lockMap.get(key).tryLock();
        }

        return lock.tryLock();
    }

    private void clearOldDataFromMap()
    {
        for (Iterator<Map.Entry<String, LocalDateTime>> it = localDateTimeMap.entrySet().iterator(); it.hasNext();)
        {
            Map.Entry<String, LocalDateTime> item = it.next();
            // 当前时间
            LocalDateTime now  = LocalDateTime.now();
            Duration duration = Duration.between(item.getValue(), now);
            if(duration.toMillis() > 60000)
            {
                it.remove();
                lockMap.remove(item.getKey());
            }
        }
    }
}
