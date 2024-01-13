package com.zhongni.bs1.service.service.local.user;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhongni.bs1.entity.db.user.User;
import com.zhongni.bs1.entity.dto.user.*;

import java.util.List;

/**
* @description 针对表【squid_user】的数据库操作Service
* @createDate 2022-12-19 16:09:10
*/
public interface UserService extends IService<User> {
    List<String> registerLocal(UseRregisterLocalInDTO useRregisterLocalInDTO);
    String login(LoginInDTO loginInDTO);
    void logout(String token);
    void resetPassword(ResetPasswordInDTO resetPasswordInDTO);
    User getLoginUserByToken(String token);
    void updateBase(UserInDTO userInDTO);
    void walletpwdUpdate(String token, WalletPasswordInDTO walletPasswordInDTO);

    User combineNewUser(UseRregisterLocalInDTO useRregisterLocalInDTO);

    void saveDuplicateNoThrow(User user);

}
