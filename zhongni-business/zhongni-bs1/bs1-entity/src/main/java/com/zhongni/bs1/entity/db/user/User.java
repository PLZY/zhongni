package com.zhongni.bs1.entity.db.user;

import com.baomidou.mybatisplus.annotation.*;
import com.zhongni.bs1.entity.db.base.BaseDO;
import com.zhongni.bs1.entity.dto.user.UserOutDTO;
import lombok.Data;

import java.util.Date;

/**
 *
 * @TableName squid_user
 */
@TableName(value ="squid_user")
@Data
public class User extends BaseDO<UserOutDTO> {
    /**
     * 用户编码
     */
    @TableId(value = "user_id", type = IdType.AUTO)
    private Long userId;

    /**
     * 钱包地址
     */
    @TableField(value = "wallet_address")
    private String walletAddress;

    /**
     * 区块链id
     */
    @TableField(value = "domain_id")
    private String domainId;

    /**
     * 昵称
     */
    @TableField(value = "nick_name")
    private String nickName;

    /**
     * 头像
     */
    @TableField(value = "image_path", insertStrategy = FieldStrategy.IGNORED)
    private String imagePath;

    /**
     * 登录密码
     */
    @TableField(value = "pass_word", insertStrategy = FieldStrategy.IGNORED)
    private String passWord;

    /**
     * 钱包密码
     */
    @TableField(value = "wallet_password")
    private String walletPassword;

    /**
     * 用户本人邀请码
     */
    @TableField(value = "own_invitation_code")
    private String ownInvitationCode;

    /**
     * 邀请人的邀请码
     */
    @TableField(value = "inviter_invitation_code", insertStrategy = FieldStrategy.IGNORED)
    private String inviterInvitationCode;

    /**
     * 邮箱地址
     */
    @TableField(value = "email_address", insertStrategy = FieldStrategy.IGNORED)
    private String emailAddress;

    /**
     * 用户状态 ENABLE-有效 DISABLE-无效
     */
    @TableField(value = "user_status")
    private String userStatus;

    /**
     * 用户类型 LOCAL-本地注册用户， THIRD-三方对接用户
     */
    @TableField(value = "user_type")
    private String userType;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Date createTime;

    /**
     * 修改时间
     */
    @TableField(value = "update_time", insertStrategy = FieldStrategy.IGNORED)
    private Date updateTime;

    /**
     * 最后一次登录的时间
     */
    @TableField(value = "last_login_time", insertStrategy = FieldStrategy.IGNORED)
    private Date lastLoginTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Override
    public UserOutDTO toDTO() {
        UserOutDTO userOutDTO = new UserOutDTO();
        userOutDTO.setUserId(this.getUserId());
        userOutDTO.setWalletAddress(this.getWalletAddress());
        userOutDTO.setDomainId(this.getDomainId());
        userOutDTO.setNickName(this.getNickName());
        userOutDTO.setImagePath(this.getImagePath());
        userOutDTO.setPassWord(this.getPassWord());
        userOutDTO.setWalletPassword(this.getWalletPassword());
        userOutDTO.setOwnInvitationCode(this.getOwnInvitationCode());
        userOutDTO.setInviterInvitationCode(this.getInviterInvitationCode());
        userOutDTO.setEmailAddress(this.getEmailAddress());
        userOutDTO.setUserStatus(this.getUserStatus());
        userOutDTO.setUserType(this.getUserType());
        userOutDTO.setCreateTime(this.getCreateTime());
        userOutDTO.setUpdateTime(this.getUpdateTime());
        userOutDTO.setLastLoginTime(this.getLastLoginTime());
        return userOutDTO;
    }
}