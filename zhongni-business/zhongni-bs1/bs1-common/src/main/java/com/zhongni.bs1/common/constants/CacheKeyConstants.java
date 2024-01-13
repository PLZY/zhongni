package com.zhongni.bs1.common.constants;

public class CacheKeyConstants {
    /**
     * 发送注册邮件验证码后 使用此key前缀 + 邮箱地址 来保存当前邮箱对应的验证码信息 用于判断后续输入注册的验证码是否正确
     */
    public static final String CACHE_VERIFICATION_PREFIX = "verification:";

    /**
     * 发送邮箱非密码（验证码）登录验证码后 使用此key前缀 + 邮箱地址 来保存当前邮箱对应的用户信息和发送的验证码信息 用于判断后续输入登录的验证码是否正确
     */
    public static final String CACHE_LOGIN_PREFIX = "login:";

    /**
     * 发送密码重置邮件验证码后 使用此key前缀 + 邮箱地址 来保存当前邮箱对应的用户信息和发送的验证码信息 用于判断后续输入重置密码的验证码是否正确
     */
    public static final String CACHE_RESET_PREFIX = "reset:";

    /**
     * 邮箱发送的各类验证码校验通过后 使用此key前缀 + 邮箱地址 缓存对应的通过信息（一般是user对象），用于标识当前操作已经通过了邮箱验证码的校验
     */
    public static final String CACHE_CHECK_PASS_PREFIX = "check_pass:";

    /**
     * 邮箱发送的注册验证码校验通过后 由于还没有User对象，使用此value充当校验通过标识
     */
    public static final String CACHE_CHECK_PASS_VALUE = "PASS";

    /**
     * 人员登录成功后 使用此key前缀 + 邮箱地址/钱包地址 缓存登录成功后的token值，用于校验重复登录请求
     */
    public static final String CACHE_EXIST_PREFIX = "exist:";

    /**
     * 人员登录成功后 使用此key前缀 + 登录后生成的token值 用于缓存当前登录人员信息
     */
    public static final String CACHE_TOKEN_PREFIX = "token:";

    /**
     * 缓存前缀
     */
    public static final String CACHE_PREFIX = "CACHE:";

    /**
     * 缓存锁前缀
     */
    public static final String CACHE_LOCK_PREFIX = "LOCK:";
}
