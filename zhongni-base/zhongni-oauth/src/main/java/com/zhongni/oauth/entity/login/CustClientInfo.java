package com.zhongni.oauth.entity.login;

import com.google.common.collect.Lists;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class CustClientInfo extends BaseClientDetails {

    /**
     * ID
     */
    private Long id;

    /**
     * 客户端ID
     */
    private String clientId;

    /**
     * 客户端名称
     */
    private String clientName;

    /**
     * 客户端secret
     */
    private String clientSecret;

    /**
     * 授权模式
     */
    private String grantType;

    /**
     * 授权成功后重定向URL
     */
    private String redirectUri;

    /**
     * 授权码校验URL
     */
    private String tokenUri;

    /**
     * 授权页面URL
     */
    private String authorizationUri;

    /**
     * 客户端提供的公钥
     */
    private String clientPublicKey;

    /**
     * 客户端提供的私钥
     */
    private String clientPrivateKey;

    /**
     * 权限级别
     */
    private String clientScope;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getClientId() {
        return clientId;
    }

    @Override
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    @Override
    public String getClientSecret() {
        return clientSecret;
    }

    @Override
    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getGrantType() {
        return grantType;
    }

    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public void setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
    }

    public String getTokenUri() {
        return tokenUri;
    }

    public void setTokenUri(String tokenUri) {
        this.tokenUri = tokenUri;
    }

    public String getAuthorizationUri() {
        return authorizationUri;
    }

    public void setAuthorizationUri(String authorizationUri) {
        this.authorizationUri = authorizationUri;
    }

    public String getClientPublicKey() {
        return clientPublicKey;
    }

    public void setClientPublicKey(String clientPublicKey) {
        this.clientPublicKey = clientPublicKey;
    }

    public String getClientPrivateKey() {
        return clientPrivateKey;
    }

    public void setClientPrivateKey(String clientPrivateKey) {
        this.clientPrivateKey = clientPrivateKey;
    }

    public String getClientScope() {
        return clientScope;
    }

    public void setClientScope(String clientScope) {
        this.clientScope = clientScope;
    }

    public ClientDetails toClientDetails()
    {
        BaseClientDetails clientDetails = new BaseClientDetails();
        clientDetails.setClientId((this.clientId));
        clientDetails.setClientSecret((this.clientSecret));
        // 必须配置了包含refresh_token的grantType，才会给前端返回refresh_token
        clientDetails.setAuthorizedGrantTypes(Lists.newArrayList(this.grantType.split(",")));
        clientDetails.setScope(Lists.newArrayList(this.clientScope));
        // 默认情况下，客户端没有任何权限
        clientDetails.setAuthorities(Collections.emptyList());
        Set<String> redirectUris = new HashSet<>();
        redirectUris.add(this.redirectUri);
        clientDetails.setRegisteredRedirectUri(redirectUris);
        return clientDetails;
    }
}
