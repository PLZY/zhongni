package com.zhongni.oauth.entity.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName user_info
 */
@TableName(value ="user_info")
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
        UserInfo other = (UserInfo) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getUserName() == null ? other.getUserName() == null : this.getUserName().equals(other.getUserName()))
            && (this.getUserNickName() == null ? other.getUserNickName() == null : this.getUserNickName().equals(other.getUserNickName()))
            && (this.getUserRealName() == null ? other.getUserRealName() == null : this.getUserRealName().equals(other.getUserRealName()))
            && (this.getPassword() == null ? other.getPassword() == null : this.getPassword().equals(other.getPassword()))
            && (this.getUserMobile() == null ? other.getUserMobile() == null : this.getUserMobile().equals(other.getUserMobile()))
            && (this.getUserIdCard() == null ? other.getUserIdCard() == null : this.getUserIdCard().equals(other.getUserIdCard()))
            && (this.getUserBirth() == null ? other.getUserBirth() == null : this.getUserBirth().equals(other.getUserBirth()))
            && (this.getUserFrom() == null ? other.getUserFrom() == null : this.getUserFrom().equals(other.getUserFrom()))
            && (this.getUserEmail() == null ? other.getUserEmail() == null : this.getUserEmail().equals(other.getUserEmail()))
            && (this.getUserAddress() == null ? other.getUserAddress() == null : this.getUserAddress().equals(other.getUserAddress()))
            && (this.getUserStatus() == null ? other.getUserStatus() == null : this.getUserStatus().equals(other.getUserStatus()))
            && (this.getLastLoginTime() == null ? other.getLastLoginTime() == null : this.getLastLoginTime().equals(other.getLastLoginTime()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getUserName() == null) ? 0 : getUserName().hashCode());
        result = prime * result + ((getUserNickName() == null) ? 0 : getUserNickName().hashCode());
        result = prime * result + ((getUserRealName() == null) ? 0 : getUserRealName().hashCode());
        result = prime * result + ((getPassword() == null) ? 0 : getPassword().hashCode());
        result = prime * result + ((getUserMobile() == null) ? 0 : getUserMobile().hashCode());
        result = prime * result + ((getUserIdCard() == null) ? 0 : getUserIdCard().hashCode());
        result = prime * result + ((getUserBirth() == null) ? 0 : getUserBirth().hashCode());
        result = prime * result + ((getUserFrom() == null) ? 0 : getUserFrom().hashCode());
        result = prime * result + ((getUserEmail() == null) ? 0 : getUserEmail().hashCode());
        result = prime * result + ((getUserAddress() == null) ? 0 : getUserAddress().hashCode());
        result = prime * result + ((getUserStatus() == null) ? 0 : getUserStatus().hashCode());
        result = prime * result + ((getLastLoginTime() == null) ? 0 : getLastLoginTime().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", userName=").append(userName);
        sb.append(", userNickName=").append(userNickName);
        sb.append(", userRealName=").append(userRealName);
        sb.append(", password=").append(password);
        sb.append(", userMobile=").append(userMobile);
        sb.append(", userIdCard=").append(userIdCard);
        sb.append(", userBirth=").append(userBirth);
        sb.append(", userFrom=").append(userFrom);
        sb.append(", userEmail=").append(userEmail);
        sb.append(", userAddress=").append(userAddress);
        sb.append(", userStatus=").append(userStatus);
        sb.append(", lastLoginTime=").append(lastLoginTime);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}