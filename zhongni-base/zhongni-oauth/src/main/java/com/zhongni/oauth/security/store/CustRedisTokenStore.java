package com.zhongni.oauth.security.store;

import com.alibaba.fastjson.JSON;
import com.zhongni.oauth.constants.OauthConstants;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Component
@DependsOn("redisConfig")
public class CustRedisTokenStore extends RedisTokenStore {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    public CustRedisTokenStore(RedisConnectionFactory connectionFactory) {
        super(connectionFactory);
    }

    @Override
    public void storeAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {
        redisTemplate.opsForValue().set(OauthConstants.REDIS_ACCESS_TOKEN_KEY_PREFIX + token.getValue(),
                JSON.toJSONString(authentication), token.getExpiresIn(), TimeUnit.SECONDS);
    }
}
