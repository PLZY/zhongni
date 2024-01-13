package com.zhongni.bs1.entity.dto.user;

import com.zhongni.bs1.common.annotation.NotNull;
import lombok.Data;

import java.io.Serializable;

@Data
public class VerificationInDTO implements Serializable {
    @NotNull
    private String emailAddress;
    @NotNull
    private String mailType;

    private String verificationCode;
}
