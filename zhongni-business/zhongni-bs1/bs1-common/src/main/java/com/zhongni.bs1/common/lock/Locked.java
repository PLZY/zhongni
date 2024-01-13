package com.zhongni.bs1.common.lock;

public interface Locked {

    /**
     * 加锁
     * @param key 加锁的key
     */
    void lock(String key);

    /**
     * 解锁
     * @param key 加锁的key
     */
    void unlock(String key);

    /**
     * 尝试加锁
     * @param key 加锁的key
     */
    boolean tryLock(String key);
}
