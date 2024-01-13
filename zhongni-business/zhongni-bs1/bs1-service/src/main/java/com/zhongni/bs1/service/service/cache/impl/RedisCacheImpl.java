package com.zhongni.bs1.service.service.cache.impl;

import com.zhongni.bs1.common.cache.Cache;
import com.zhongni.bs1.common.constants.CacheKeyConstants;
import com.zhongni.bs1.common.util.CacheUtil;
import com.zhongni.bs1.common.util.LockUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service("redisCacheServiceImpl")
public class RedisCacheImpl implements Cache {

    @Autowired
    // todo 此处可优化用StringRedisTemplate，自己手动序列化成json存成字符串比较节省内存空间，直接序列化对象在真正存储到redis时会有对象地址标识
    private RedisTemplate<String, Object> redisTemplate;
    @Value("${no.operation.timeout:600}")
    private long noOperationTimeOut;

    @Override
    public void put(String key, Object value, long duration, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, value, duration, unit);
    }

    @Override
    public <T> T get(String key, Class<T> clazz) {
        Object value = redisTemplate.opsForValue().get(key);
        // 每获取一次登录的token,则对当前的token进行一次续期
        if(key.startsWith(CacheKeyConstants.CACHE_PREFIX + CacheKeyConstants.CACHE_TOKEN_PREFIX) && null != value)
        {
            put(key, value, noOperationTimeOut, TimeUnit.SECONDS);
        }

        return clazz.cast(value);
    }

    @Override
    public Object get(String key) {
        return get(key, Object.class);
    }

    @Override
    public void del(String key) {
        redisTemplate.delete(key);
    }

    @Override
    public Integer autoInc(String key) {
        // todo 感觉使用redis原子自增方法实现更加合理一些
        return LockUtil.syncExecute(key, key, item -> {
            // 通过key，实现单秒自增；不同的key，从1开始自增，同时设置1分钟过期
            Integer incrId = null == CacheUtil.get(item) ? 1 : CacheUtil.get(item, Integer.class);
            CacheUtil.put(item, ++incrId, 1, TimeUnit.MINUTES);
            return incrId;
        });
    }
}
