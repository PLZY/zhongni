package com.zhongni.oauth.entity.login;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.Date;

public class UserAuthInfo extends User {


    /**
     * ID
     */
    private Long id;

    /**
     * 昵称
     */
    private String userNickName;

    /**
     * 真实姓名
     */
    private String userRealName;

    /**
     * 手机号
     */
    private String userMobile;

    /**
     * 身份证号
     */
    private String userIdCard;

    /**
     * 生日
     */
    private String userBirth;

    /**
     * 用户来源
     */
    private String userFrom;

    /**
     * 邮件
     */
    private String userEmail;

    /**
     * 住址
     */
    private String userAddress;

    /**
     * 用户状态 ENABLE-有效 DISABLE-无效
     */
    private String userStatus;

    /**
     * 最后登录时间
     */
    private Date lastLoginTime;


    private static final long serialVersionUID = 1L;


    public UserAuthInfo(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public UserAuthInfo(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }

    public UserAuthInfo setOtherUserAuthInfoProperties(Long id, String userNickName, String userRealName,
                                                       String userMobile, String userIdCard,
                                                       String userBirth, String userFrom, String userEmail,
                                                       String userAddress, String userStatus, Date lastLoginTime) {
        this.id = id;
        this.userNickName = userNickName;
        this.userRealName = userRealName;
        this.userMobile = userMobile;
        this.userIdCard = userIdCard;
        this.userBirth = userBirth;
        this.userFrom = userFrom;
        this.userEmail = userEmail;
        this.userAddress = userAddress;
        this.userStatus = userStatus;
        this.lastLoginTime = lastLoginTime;
        return this;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserNickName() {
        return userNickName;
    }

    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
    }

    public String getUserRealName() {
        return userRealName;
    }

    public void setUserRealName(String userRealName) {
        this.userRealName = userRealName;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public String getUserIdCard() {
        return userIdCard;
    }

    public void setUserIdCard(String userIdCard) {
        this.userIdCard = userIdCard;
    }

    public String getUserBirth() {
        return userBirth;
    }

    public void setUserBirth(String userBirth) {
        this.userBirth = userBirth;
    }

    public String getUserFrom() {
        return userFrom;
    }

    public void setUserFrom(String userFrom) {
        this.userFrom = userFrom;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }
}
