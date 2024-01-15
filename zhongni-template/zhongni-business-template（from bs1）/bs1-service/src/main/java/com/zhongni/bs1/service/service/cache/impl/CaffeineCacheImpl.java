package com.zhongni.bs1.service.service.cache.impl;

import com.alibaba.fastjson.JSON;
import com.zhongni.bs1.common.cache.Cache;
import com.zhongni.bs1.common.constants.CacheKeyConstants;
import com.zhongni.bs1.common.util.CacheUtil;
import com.zhongni.bs1.common.util.LockUtil;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Expiry;
import com.github.benmanes.caffeine.cache.RemovalListener;
import com.github.benmanes.caffeine.cache.Weigher;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.util.RamUsageEstimator;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service("caffeineCacheServiceImpl")
public class CaffeineCacheImpl implements Cache {

    private static com.github.benmanes.caffeine.cache.Cache<String, Object> cache;

    /**
     * 登录用户无操作多久后自动退出登录（单位：秒），默认10分钟
     */
    @Value("${no.operation.timeout:600}")
    private long noOperationTimeOut;

    {
        cache = Caffeine.newBuilder()
                // 80M内存
                .maximumWeight(83886080)
                .weigher(new Weigher<Object, Object>() {
                    @Override
                    public @NonNegative int weigh(@NonNull Object key, @NonNull Object value) {
                        return Math.toIntExact(RamUsageEstimator.sizeOfObject(value));
                    }
                })
                .expireAfter(new Expiry<Object, Object>() {
                    @Override
                    public long expireAfterCreate(@NonNull Object key, @NonNull Object value, long currentTime) {
                        return currentTime;
                    }

                    @Override
                    public long expireAfterUpdate(@NonNull Object key, @NonNull Object value, long currentTime, @NonNegative long currentDuration) {
                        return currentDuration;
                    }

                    @Override
                    public long expireAfterRead(@NonNull Object key, @NonNull Object value, long currentTime, @NonNegative long currentDuration) {
                        String keyStr = (String)key;
                        // 如果是登录用户的token验证，每获取一次证明该用户还处于活动中，新增noOperationTimeOut秒的有效时间
                        if(keyStr.startsWith(CacheKeyConstants.CACHE_PREFIX + CacheKeyConstants.CACHE_TOKEN_PREFIX))
                        {
                            // 缓存续期时间的单位是纳秒，配置项单位是秒，此处需要进行换算
                            return noOperationTimeOut * 1000000000L;
                        }

                        // 其他的
                        return currentDuration;
                    }
                })
                .removalListener((RemovalListener<String, Object>) (s, o, removalCause) -> {
                    log.debug("system remove cache key[{}], value[{}], removalCause is [{}]", s, JSON.toJSONString(o), removalCause);
                })
                // 初始的缓存空间大小
                .initialCapacity(100)
                .build();
    }

    /**
     * 放置缓存
     * @param key 缓存键
     * @param value 缓存值
     * @param duration 超时时间
     * @param unit 超时时间单位
     */
    @Override
    public void put(String key, Object value, long duration, TimeUnit unit)
    {
        cache.policy().expireVariably().ifPresent(policy -> policy.put(key, value, duration, unit));
    }

    /**
     * 返回指定对象类型的缓存值
     * @param key 缓存键
     * @param clazz 指定的对象类型
     */
    @Override
    public <T> T get(String key, Class<T> clazz)
    {
        log.debug("current cache content is {}", cache.asMap());
        return clazz.cast(cache.getIfPresent(key));
    }

    /**
     * 返回object类型（兼容之前的逻辑）
     * @param key 缓存键
     */
    @Override
    public Object get(String key)
    {
        return get(key, Object.class);
    }

    /**
     * 缓存删除
     * @param key 缓存键
     */
    @Override
    public void del(String key)
    {
        cache.invalidate(key);
    }

    @Override
    public Integer autoInc(String key) {
        return LockUtil.syncExecute(key, key, item -> {
            // 通过key，实现单秒自增；不同的key，从1开始自增，同时设置1分钟过期
            Integer incrId = null == CacheUtil.get(item) ? 1 : CacheUtil.get(item, Integer.class);
            CacheUtil.put(item, ++incrId, 1, TimeUnit.MINUTES);
            return incrId;
        });
    }
}
