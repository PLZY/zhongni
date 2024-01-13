package com.zhongni.bs1.entity.dto.user;

import com.zhongni.bs1.entity.dto.base.BaseOutDTO;
import lombok.Data;

import java.util.Date;

@Data
public class UserOutDTO extends BaseOutDTO {
    /**
     * 用户编码
     */
    private Long userId;

    /**
     * 钱包地址
     */
    private String walletAddress;

    /**
     * 区块链id
     */
    private String domainId;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 头像
     */
    private String imagePath;

    /**
     * 登录密码
     */
    private String passWord;

    /**
     * 钱包密码
     */
    private String walletPassword;

    /**
     * 用户本人邀请码
     */
    private String ownInvitationCode;

    /**
     * 邀请人的邀请码
     */
    private String inviterInvitationCode;

    /**
     * 邮箱地址
     */
    private String emailAddress;

    /**
     * 用户状态 ENABLE-有效 DISABLE-无效
     */
    private String userStatus;

    /**
     * 用户类型 LOCAL-本地注册用户， THIRD-三方对接用户
     */
    private String userType;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 最后一次登录的时间
     */
    private Date lastLoginTime;

    private static final long serialVersionUID = 1L;

    public String getImagePath() {
        return imagePath;
    }

}