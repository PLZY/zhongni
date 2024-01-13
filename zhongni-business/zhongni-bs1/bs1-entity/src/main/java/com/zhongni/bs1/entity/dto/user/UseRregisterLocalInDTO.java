package com.zhongni.bs1.entity.dto.user;

import com.zhongni.bs1.common.annotation.NotNull;
import com.zhongni.bs1.common.constants.BusinessConstants;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

@Data
public class UseRregisterLocalInDTO implements Serializable {
    // 昵称
    private String nickName;

    // 头像文件（用户自定义头像使用）
    private MultipartFile imgFile;

    // 头像地址（选择默认头像时使用）
    private String imgPath;

    // 登录密码（以后可能用验证码登录或者三方登录，但密码注册时还是必填的）
    @NotNull
    private String passWord;

    // 邀请码
    private String invitationCode;

    @NotNull
    private String domainId;

    // 邮箱地址
    @NotNull
    private String emailAddress;

    // 钱包密码（用于查看钱包信息时需要验证此密码）
    @NotNull
    private String walletPassword;
}
