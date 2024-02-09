package com.zhongni.oauth.config;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.zhongni.oauth.constants.OauthConstants;
import com.zhongni.oauth.security.store.CustRedisTokenStore;
import com.zhongni.oauth.service.login.DBClientDetailsService;
import com.zhongni.oauth.service.login.DBUserDetailsService;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.common.util.RandomValueStringGenerator;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Configuration
@DependsOn("securityConfig")
public class Oauth2ServerConfig extends AuthorizationServerConfigurerAdapter {

    private RandomValueStringGenerator generator = new RandomValueStringGenerator();
    @Resource
    private DBClientDetailsService dbClientDetailsService;

    @Resource
    private DBUserDetailsService dbUserDetailsService;

    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    private RedisConnectionFactory factory;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.tokenKeyAccess("permitAll()").checkTokenAccess("permitAll()").allowFormAuthenticationForClients();
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(dbClientDetailsService);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager)
                .userDetailsService(dbUserDetailsService)
                .authorizationCodeServices(new AuthorizationCodeServices() {
                       @Override
                       public String createAuthorizationCode(OAuth2Authentication authentication) {
                           String code = generator.generate();
                           redisTemplate.opsForValue().set(OauthConstants.REDIS_AUTHORIZATION_CODE_KEY_PREFIX + code, JSON.toJSONString(authentication), 3, TimeUnit.MINUTES);
                           return code;
                       }

                       @Override
                       public OAuth2Authentication consumeAuthorizationCode(String code) throws InvalidGrantException {
                           String authenticationJson = String.valueOf(redisTemplate.opsForValue().getAndSet(OauthConstants.REDIS_AUTHORIZATION_CODE_KEY_PREFIX + code, "invalid"));
                           if(null == authenticationJson || "invalid".equals(authenticationJson))
                           {
                               return null;
                           }

                           redisTemplate.delete(OauthConstants.REDIS_AUTHORIZATION_CODE_KEY_PREFIX + code);
                           JSONObject jsonObject = JSON.parseObject(authenticationJson);
                           JSONObject oAuth2RequestJson = jsonObject.getJSONObject("oAuth2Request");
                           // 创建一个 HashSet 来存储去重后的字符串
                           Set<SimpleGrantedAuthority> authoritiesSet = new HashSet<>();
                           JSONArray authoritiesJSONArray = oAuth2RequestJson.getJSONArray("authorities");
                           // 遍历 JSONArray 并将每个元素添加到 HashSet 中
                           for (int i = 0; i < authoritiesJSONArray.size(); i++) {
                               SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(authoritiesJSONArray.getString(i));
                               authoritiesSet.add(simpleGrantedAuthority);
                           }

                           // 创建一个 HashSet 来存储去重后的字符串
                           Set<String> scopeSet = new HashSet<>();
                           JSONArray scopeJSONArray = oAuth2RequestJson.getJSONArray("scope");
                           // 遍历 JSONArray 并将每个元素添加到 HashSet 中
                           for (int i = 0; i < scopeJSONArray.size(); i++) {
                               scopeSet.add(scopeJSONArray.getString(i));
                           }

                           // 创建一个 HashSet 来存储去重后的字符串
                           Set<String> resourceIdsSet = new HashSet<>();
                           JSONArray resourceIdsJSONArray = oAuth2RequestJson.getJSONArray("resourceIds");
                           // 遍历 JSONArray 并将每个元素添加到 HashSet 中
                           for (int i = 0; i < resourceIdsJSONArray.size(); i++) {
                               resourceIdsSet.add(resourceIdsJSONArray.getString(i));
                           }

                           // 创建一个 HashSet 来存储去重后的字符串
                           Set<String> responseTypesSet = new HashSet<>();
                           JSONArray responseTypesJSONArray = oAuth2RequestJson.getJSONArray("responseTypes");
                           // 遍历 JSONArray 并将每个元素添加到 HashSet 中
                           for (int i = 0; i < responseTypesJSONArray.size(); i++) {
                               responseTypesSet.add(responseTypesJSONArray.getString(i));
                           }

                           JSONObject requestParametersJson = oAuth2RequestJson.getJSONObject("requestParameters");
                           Map<String, String> requestParametersMap = requestParametersJson.toJavaObject(new TypeReference<Map<String, String>>() {});

                           JSONObject extensionsJson = oAuth2RequestJson.getJSONObject("extensions");
                           Map<String, Serializable> extensionsJsonMap = extensionsJson.toJavaObject(new TypeReference<Map<String, Serializable>>() {});
                           OAuth2Request storedRequest = new OAuth2Request(requestParametersMap, oAuth2RequestJson.getString("clientId"),
                                   authoritiesSet, oAuth2RequestJson.getBooleanValue("approved"), scopeSet,
                                   resourceIdsSet, oAuth2RequestJson.getString("redirectUri"), responseTypesSet,
                                   extensionsJsonMap);

                           Authentication userAuthentication = new UsernamePasswordAuthenticationToken(jsonObject.getJSONObject("principal"), jsonObject.getString("credentials"), authoritiesSet);
                           OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(storedRequest, userAuthentication);
                           return oAuth2Authentication;
                       }
                   }
                )
                .tokenServices(authorizationServerTokenServices())
                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST);
    }

    private AuthorizationServerTokenServices authorizationServerTokenServices(){
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setClientDetailsService(dbClientDetailsService);
        defaultTokenServices.setTokenStore(tokenStore());
        defaultTokenServices.setAccessTokenValiditySeconds(7200);
        defaultTokenServices.setRefreshTokenValiditySeconds(86400);
        return defaultTokenServices;
    }

    private TokenStore tokenStore() {
        return new CustRedisTokenStore(factory);
    }
}
