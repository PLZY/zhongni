package com.zhongni.bs1.common.util;

import com.zhongni.bs1.common.cache.Cache;
import com.zhongni.bs1.common.constants.BusinessConstants;
import com.zhongni.bs1.common.constants.CacheKeyConstants;
import com.zhongni.bs1.common.enums.CacheTypeEnum;
import com.zhongni.bs1.common.spring.AppContext;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class CacheUtil {
    private static final Cache cache = AppContext.getBean(CacheTypeEnum.getImplClass(
            AppContext.getApplicationContext().getEnvironment().getProperty(BusinessConstants.DEPLOYMENT_MODE)), Cache.class);

    private CacheUtil() {}
    /**
     * 放置缓存
     * @param key 缓存键
     * @param value 缓存值
     * @param duration 超时时间
     * @param unit 超时时间单位
     */
    public static void put(String key, Object value, long duration, TimeUnit unit)
    {
        String realKey = CacheKeyConstants.CACHE_PREFIX + key;
        log.debug("put value by key[{}]", realKey);
        cache.put(realKey, value, duration,unit);
    }

    /**
     * 返回指定对象类型的缓存值
     * @param key 缓存键
     * @param clazz 指定的对象类型
     */
    public static <T> T get(String key, Class<T> clazz)
    {
        String realKey = CacheKeyConstants.CACHE_PREFIX + key;
        log.debug("get value by key[{}]", realKey);
        return cache.get(realKey, clazz);
    }

    /**
     * 返回object类型（兼容之前的逻辑）
     * @param key 缓存键
     */
    public static Object get(String key)
    {
        return get(key, Object.class);
    }

    /**
     * 缓存删除
     * @param key 缓存键
     */
    public static void del(String key)
    {
        String realKey = CacheKeyConstants.CACHE_PREFIX + key;
        log.debug("del value by key[{}]", realKey);
        cache.del(CacheKeyConstants.CACHE_PREFIX + key);
    }

    /**
     * 自增，每获取一次key，在之前的基础上对应的值增加一
     * @param key 缓存键
     */
    public static Integer autoInc(String key){
        return cache.autoInc(key);
    }
}
