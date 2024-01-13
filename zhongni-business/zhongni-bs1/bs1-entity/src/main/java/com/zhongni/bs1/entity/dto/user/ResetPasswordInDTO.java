package com.zhongni.bs1.entity.dto.user;

import com.zhongni.bs1.common.annotation.NotNull;
import lombok.Data;

import java.io.Serializable;

@Data
public class ResetPasswordInDTO implements Serializable {
    // 邮箱地址
    @NotNull
    private String emailAddress;

    // 密码
    @NotNull
    private String passWord;
}
