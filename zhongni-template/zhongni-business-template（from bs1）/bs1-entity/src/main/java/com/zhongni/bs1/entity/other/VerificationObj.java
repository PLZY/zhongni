package com.zhongni.bs1.entity.other;

import com.zhongni.bs1.entity.db.user.User;
import lombok.Data;

@Data
public class VerificationObj {
    /**
     * 验证码
     */
    private String verificationCode;

    /**
     * 人员信息，验证码登录和密码重置的时候会用
     */
    private User user;

    public VerificationObj(String verificationCode)
    {
        this.verificationCode = verificationCode;
    }

    public VerificationObj(String verificationCode, User user) {
        this.verificationCode = verificationCode;
        this.user = user;
    }

    public VerificationObj(){}
}
