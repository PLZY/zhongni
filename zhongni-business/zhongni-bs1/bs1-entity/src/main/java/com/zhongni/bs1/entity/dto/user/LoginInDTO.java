package com.zhongni.bs1.entity.dto.user;

import com.zhongni.bs1.common.annotation.NotNull;
import com.zhongni.bs1.entity.dto.base.BaseInDTO;
import lombok.Data;

@Data
public class LoginInDTO extends BaseInDTO {
    @NotNull
    private String loginType;

    //======================三方登录===========================
    // 钱包地址域ID
    private String domainId;

    // 钱包地址，三方登录才会传，本地登录不需要
    private String walletAddress;

    // 邀请码，三方登录时可能会先注册
    private String invitationCode;

    //======================本地登录===========================
    // 邮箱地址，本地登录才会传，三方登录不需要
    private String emailAddress;

    // 验证码
    private String verificationCode;

    // 密码（以后可能用验证码登录或者三方登录，密码目前来看不是必填的）
    private String passWord;
}
