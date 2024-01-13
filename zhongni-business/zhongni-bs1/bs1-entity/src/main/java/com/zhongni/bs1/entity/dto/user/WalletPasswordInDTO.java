package com.zhongni.bs1.entity.dto.user;

import lombok.Data;

import java.io.Serializable;

@Data
public class WalletPasswordInDTO implements Serializable {
    // 修改后的钱包密码
    private String walletPassword;

    // 旧的钱包密码
    private String oldWalletPassword;
}
