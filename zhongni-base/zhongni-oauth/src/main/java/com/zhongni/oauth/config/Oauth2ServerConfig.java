package com.zhongni.oauth.config;


import com.zhongni.oauth.entity.client.ClientInfo;
import com.zhongni.oauth.service.client.ClientInfoService;
import com.zhongni.oauth.service.login.DBClientDetailsService;
import com.zhongni.oauth.service.login.DBUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.common.util.RandomValueStringGenerator;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@DependsOn("securityConfig")
public class Oauth2ServerConfig extends AuthorizationServerConfigurerAdapter {
    private final Map<String, OAuth2Authentication> authorizationCodeStore = new HashMap<>();
    private final RandomValueStringGenerator generator = new RandomValueStringGenerator();

    @Resource
    private DBClientDetailsService dbClientDetailsService;

    @Resource
    private DBUserDetailsService dbUserDetailsService;

    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    private ClientInfoService clientInfoService;

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        //return new RedisClientRegistrationRepository(redisTemplate);
        return new InMemoryClientRegistrationRepository(clientRegistration());
    }

    private ClientRegistration[] clientRegistration() {
        List<ClientInfo> list = clientInfoService.list();
        ClientRegistration[] clientRegistrations = new ClientRegistration[list.size()];
        for (int i = 0; i < list.size(); i++) {
            ClientInfo clientInfo = list.get(i);
            clientRegistrations[i] = ClientRegistration.withRegistrationId(clientInfo.getClientId())
                    .clientId(clientInfo.getClientId())
                    .clientSecret(clientInfo.getClientSecret())
                    .scope(clientInfo.getClientScope())
                    .authorizationGrantType(new AuthorizationGrantType(clientInfo.getGrantType()))
                    .redirectUri(clientInfo.getRedirectUri())
                    .authorizationUri("/oauth/authorize")
                    .tokenUri("https://www.baidu.com")
                    .build();
        }

        return clientRegistrations;
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
        return new InMemoryTokenStore();
    }

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
                    public String createAuthorizationCode(OAuth2Authentication oAuth2Authentication) {
                        String code = generator.generate();
                        authorizationCodeStore.put(code, oAuth2Authentication);
                        return code;
                    }

                    @Override
                    public OAuth2Authentication consumeAuthorizationCode(String code) throws InvalidGrantException {
                        return authorizationCodeStore.remove(code);
                    }
                })
                .tokenServices(authorizationServerTokenServices())
                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST);
    }
}
