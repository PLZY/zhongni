package com.zhongni.oauth.entity.client;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

/**
 * 三方应用信息
 * @TableName client_info
 */
@TableName(value ="client_info")
public class ClientInfo implements Serializable {
    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 客户端ID
     */
    @TableField(value = "client_id")
    private String clientId;

    /**
     * 客户端名称
     */
    @TableField(value = "client_name")
    private String clientName;

    /**
     * 客户端secret
     */
    @TableField(value = "client_secret")
    private String clientSecret;

    /**
     * 授权模式
     */
    @TableField(value = "grant_type")
    private String grantType;

    /**
     * 授权成功后重定向URL
     */
    @TableField(value = "redirect_uri")
    private String redirectUri;

    /**
     * 授权码校验URL
     */
    @TableField(value = "token_uri")
    private String tokenUri;

    /**
     * 授权页面URL
     */
    @TableField(value = "authorization_uri")
    private String authorizationUri;

    /**
     * 客户端提供的公钥
     */
    @TableField(value = "client_public_key")
    private String clientPublicKey;

    /**
     * 客户端提供的私钥
     */
    @TableField(value = "client_private_key")
    private String clientPrivateKey;

    /**
     * 权限级别
     */
    @TableField(value = "client_scope")
    private String clientScope;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    public Long getId() {
        return id;
    }

    /**
     * ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 客户端ID
     */
    public String getClientId() {
        return clientId;
    }

    /**
     * 客户端ID
     */
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    /**
     * 客户端名称
     */
    public String getClientName() {
        return clientName;
    }

    /**
     * 客户端名称
     */
    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    /**
     * 客户端secret
     */
    public String getClientSecret() {
        return clientSecret;
    }

    /**
     * 客户端secret
     */
    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    /**
     * 授权模式
     */
    public String getGrantType() {
        return grantType;
    }

    /**
     * 授权模式
     */
    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }

    /**
     * 授权成功后重定向URL
     */
    public String getRedirectUri() {
        return redirectUri;
    }

    /**
     * 授权成功后重定向URL
     */
    public void setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
    }

    /**
     * 授权码校验URL
     */
    public String getTokenUri() {
        return tokenUri;
    }

    /**
     * 授权码校验URL
     */
    public void setTokenUri(String tokenUri) {
        this.tokenUri = tokenUri;
    }

    /**
     * 授权页面URL
     */
    public String getAuthorizationUri() {
        return authorizationUri;
    }

    /**
     * 授权页面URL
     */
    public void setAuthorizationUri(String authorizationUri) {
        this.authorizationUri = authorizationUri;
    }

    /**
     * 客户端提供的公钥
     */
    public String getClientPublicKey() {
        return clientPublicKey;
    }

    /**
     * 客户端提供的公钥
     */
    public void setClientPublicKey(String clientPublicKey) {
        this.clientPublicKey = clientPublicKey;
    }

    /**
     * 客户端提供的私钥
     */
    public String getClientPrivateKey() {
        return clientPrivateKey;
    }

    /**
     * 客户端提供的私钥
     */
    public void setClientPrivateKey(String clientPrivateKey) {
        this.clientPrivateKey = clientPrivateKey;
    }

    /**
     * 权限级别
     */
    public String getClientScope() {
        return clientScope;
    }

    /**
     * 权限级别
     */
    public void setClientScope(String clientScope) {
        this.clientScope = clientScope;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        ClientInfo other = (ClientInfo) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getClientId() == null ? other.getClientId() == null : this.getClientId().equals(other.getClientId()))
                && (this.getClientName() == null ? other.getClientName() == null : this.getClientName().equals(other.getClientName()))
                && (this.getClientSecret() == null ? other.getClientSecret() == null : this.getClientSecret().equals(other.getClientSecret()))
                && (this.getGrantType() == null ? other.getGrantType() == null : this.getGrantType().equals(other.getGrantType()))
                && (this.getRedirectUri() == null ? other.getRedirectUri() == null : this.getRedirectUri().equals(other.getRedirectUri()))
                && (this.getTokenUri() == null ? other.getTokenUri() == null : this.getTokenUri().equals(other.getTokenUri()))
                && (this.getAuthorizationUri() == null ? other.getAuthorizationUri() == null : this.getAuthorizationUri().equals(other.getAuthorizationUri()))
                && (this.getClientPublicKey() == null ? other.getClientPublicKey() == null : this.getClientPublicKey().equals(other.getClientPublicKey()))
                && (this.getClientPrivateKey() == null ? other.getClientPrivateKey() == null : this.getClientPrivateKey().equals(other.getClientPrivateKey()))
                && (this.getClientScope() == null ? other.getClientScope() == null : this.getClientScope().equals(other.getClientScope()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getClientId() == null) ? 0 : getClientId().hashCode());
        result = prime * result + ((getClientName() == null) ? 0 : getClientName().hashCode());
        result = prime * result + ((getClientSecret() == null) ? 0 : getClientSecret().hashCode());
        result = prime * result + ((getGrantType() == null) ? 0 : getGrantType().hashCode());
        result = prime * result + ((getRedirectUri() == null) ? 0 : getRedirectUri().hashCode());
        result = prime * result + ((getTokenUri() == null) ? 0 : getTokenUri().hashCode());
        result = prime * result + ((getAuthorizationUri() == null) ? 0 : getAuthorizationUri().hashCode());
        result = prime * result + ((getClientPublicKey() == null) ? 0 : getClientPublicKey().hashCode());
        result = prime * result + ((getClientPrivateKey() == null) ? 0 : getClientPrivateKey().hashCode());
        result = prime * result + ((getClientScope() == null) ? 0 : getClientScope().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", clientId=").append(clientId);
        sb.append(", clientName=").append(clientName);
        sb.append(", clientSecret=").append(clientSecret);
        sb.append(", grantType=").append(grantType);
        sb.append(", redirectUri=").append(redirectUri);
        sb.append(", tokenUri=").append(tokenUri);
        sb.append(", authorizationUri=").append(authorizationUri);
        sb.append(", clientPublicKey=").append(clientPublicKey);
        sb.append(", clientPrivateKey=").append(clientPrivateKey);
        sb.append(", clientScope=").append(clientScope);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}