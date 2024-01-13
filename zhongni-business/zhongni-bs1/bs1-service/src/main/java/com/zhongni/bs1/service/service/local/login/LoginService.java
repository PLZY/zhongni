package com.zhongni.bs1.service.service.local.login;

import com.zhongni.bs1.common.constants.CacheKeyConstants;
import com.zhongni.bs1.common.util.CacheUtil;
import com.zhongni.bs1.entity.db.user.User;
import com.zhongni.bs1.entity.dto.user.LoginInDTO;
import org.apache.commons.lang3.StringUtils;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

public interface LoginService {
    String login(LoginInDTO loginInDTO);
    String hasLogin(LoginInDTO loginInDTO);

    default String hasLogin(String tokenCacheKey) {
        String token = CacheUtil.get(tokenCacheKey, String.class);
        if(StringUtils.isBlank(token)) {
            return null;
        }

        if(null == CacheUtil.get(CacheKeyConstants.CACHE_TOKEN_PREFIX + token, User.class)) {
            return null;
        }

        return token;
    }

    default String generateLoginToken(User loginUser, String tokenCacheKey, long noOperationTimeOut)
    {
        String token = UUID.randomUUID().toString().replace("-", "");
        // todo 直接将整个用户对象放入内存是比较浪费的粒度比较粗，其实最好是转成DTO对象后存储将一些不需要的字段去掉，前端获取时也可直接返回不需要其他逻辑处理
        CacheUtil.put(CacheKeyConstants.CACHE_TOKEN_PREFIX + token, loginUser, noOperationTimeOut, TimeUnit.SECONDS);
        // 缓存一下当前用户是否已经登录过了，如果已经登录过了直接返回这个token即可不需要再进行其他逻辑判断
        CacheUtil.put(tokenCacheKey, token, 1, TimeUnit.DAYS);
        return token;
    }
}
