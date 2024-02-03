package com.zhongni.oauth.entity.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * 
 * @TableName user_info
 */
@TableName(value ="user_info")
@Data
public class UserInfo implements Serializable {

    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 昵称
     */
    private String userNickName;

    /**
     * 真实姓名
     */
    private String userRealName;

    /**
     * 密码
     */
    private String password;

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

    /**
     * 
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @TableField(exist = false)
    private boolean accountNonExpired = true;

    @TableField(exist = false)
    private boolean accountNonLocked = true;

    @TableField(exist = false)
    private boolean credentialsNonExpired = true;

    @TableField(exist = false)
    private Set<GrantedAuthority> authorities;

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
     * 用户名
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 用户名
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * 昵称
     */
    public String getUserNickName() {
        return userNickName;
    }

    /**
     * 昵称
     */
    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
    }

    /**
     * 真实姓名
     */
    public String getUserRealName() {
        return userRealName;
    }

    /**
     * 真实姓名
     */
    public void setUserRealName(String userRealName) {
        this.userRealName = userRealName;
    }

    /**
     * 密码
     */
    public String getPassword() {
        return password;
    }


    /**
     * 密码
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 手机号
     */
    public String getUserMobile() {
        return userMobile;
    }

    /**
     * 手机号
     */
    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    /**
     * 身份证号
     */
    public String getUserIdCard() {
        return userIdCard;
    }

    /**
     * 身份证号
     */
    public void setUserIdCard(String userIdCard) {
        this.userIdCard = userIdCard;
    }

    /**
     * 生日
     */
    public String getUserBirth() {
        return userBirth;
    }

    /**
     * 生日
     */
    public void setUserBirth(String userBirth) {
        this.userBirth = userBirth;
    }

    /**
     * 用户来源
     */
    public String getUserFrom() {
        return userFrom;
    }

    /**
     * 用户来源
     */
    public void setUserFrom(String userFrom) {
        this.userFrom = userFrom;
    }

    /**
     * 邮件
     */
    public String getUserEmail() {
        return userEmail;
    }

    /**
     * 邮件
     */
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    /**
     * 住址
     */
    public String getUserAddress() {
        return userAddress;
    }

    /**
     * 住址
     */
    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    /**
     * 用户状态 ENABLE-有效 DISABLE-无效
     */
    public String getUserStatus() {
        return userStatus;
    }

    /**
     * 用户状态 ENABLE-有效 DISABLE-无效
     */
    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    /**
     * 最后登录时间
     */
    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    /**
     * 最后登录时间
     */
    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    /**
     * 
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 修改时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 修改时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}