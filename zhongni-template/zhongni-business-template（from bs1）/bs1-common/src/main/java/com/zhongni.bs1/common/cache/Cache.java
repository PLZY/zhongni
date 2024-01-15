package com.zhongni.bs1.common.cache;

import java.util.concurrent.TimeUnit;


public interface Cache {
    /**
     * 放置缓存
     * @param key 缓存键
     * @param value 缓存值
     * @param duration 超时时间
     * @param unit 超时时间单位
     */
    void put(String key, Object value, long duration, TimeUnit unit);

    /**
     * 返回指定对象类型的缓存值
     * @param key 缓存键
     * @param clazz 指定的对象类型
     */
    <T> T get(String key, Class<T> clazz);

    /**
     * 返回object类型（兼容之前的逻辑）
     * @param key 缓存键
     */
    Object get(String key);

    /**
     * 缓存删除
     * @param key 缓存键
     */
    void del(String key);

    /**
     * 自增，每获取一次key，在之前的基础上对应的值增加一
     * @param key 缓存键
     */
    Integer autoInc(String key);
}
