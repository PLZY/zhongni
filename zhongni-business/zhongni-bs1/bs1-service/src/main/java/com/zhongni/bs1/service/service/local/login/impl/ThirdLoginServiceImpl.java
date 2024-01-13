package com.zhongni.bs1.service.service.local.login.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zhongni.bs1.common.constants.BusinessConstants;
import com.zhongni.bs1.common.constants.CacheKeyConstants;
import com.zhongni.bs1.common.enums.BusinessExceptionEnum;
import com.zhongni.bs1.common.enums.UserStatusEnum;
import com.zhongni.bs1.common.enums.UserTypeEnum;
import com.zhongni.bs1.common.exception.BusinessException;
import com.zhongni.bs1.service.service.local.login.LoginService;
import com.zhongni.bs1.service.service.local.user.UserService;
import com.zhongni.bs1.entity.db.user.User;
import com.zhongni.bs1.entity.dto.user.LoginInDTO;
import com.zhongni.bs1.entity.dto.user.UseRregisterLocalInDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;

import static com.zhongni.bs1.common.constants.BusinessConstants.DEFAULT_IMAGE_PATH;

@Service("thirdLoginServiceImpl")
public class ThirdLoginServiceImpl implements LoginService {

    /**
     * 登录用户无操作多久后自动退出登录（单位：秒），默认10分钟
     */
    @Value("${no.operation.timeout:600}")
    private long noOperationTimeOut;

    @Autowired
    private UserService userService;

    /**
     * 三方登录
     * @param loginInDTO 登录入参对象
     */
    @Override
    public String login(LoginInDTO loginInDTO) {
        String tokenCacheKey = CacheKeyConstants.CACHE_EXIST_PREFIX + loginInDTO.getDomainId() + loginInDTO.getWalletAddress();
        // 三方登录
        if(StringUtils.isBlank(loginInDTO.getDomainId()))
        {
            throw new BusinessException(BusinessExceptionEnum.PARAMS_EXCEPTION_LOGIN_DOMAIN_ID_IS_NULL);
        }

        if(StringUtils.isBlank(loginInDTO.getWalletAddress()))
        {
            throw new BusinessException(BusinessExceptionEnum.PARAMS_EXCEPTION_WALLET_ADDRESS_IS_NULL);
        }

        User loginUser = userService.getOne(new LambdaQueryWrapper<User>().eq(User::getDomainId, loginInDTO.getDomainId())
                .eq(User::getWalletAddress, loginInDTO.getWalletAddress())
                .eq(User::getUserStatus, UserStatusEnum.ENABLE.getCode())
                .eq(User::getUserType, loginInDTO.getLoginType()));
        // 如果用户是空的说明是第一次登录，直接创建一个新的用户
        if(null == loginUser)
        {
            UseRregisterLocalInDTO useRregisterLocalInDTO = new UseRregisterLocalInDTO();
            useRregisterLocalInDTO.setDomainId(loginInDTO.getDomainId());
            useRregisterLocalInDTO.setInvitationCode(loginInDTO.getInvitationCode());
            User user = userService.combineNewUser(useRregisterLocalInDTO);
            user.setUserType(UserTypeEnum.THIRD.getCode());
            user.setWalletAddress(loginInDTO.getWalletAddress());
            user.setUserStatus(UserStatusEnum.ENABLE.getCode());
            user.setLastLoginTime(new Date());
            user.setImagePath(BusinessConstants.DEFAULT_IMAGE_PATH);
            // 三方登录的用户可以先放默认登录密码
            user.setWalletPassword(DigestUtils.md5DigestAsHex(BusinessConstants.DEFAULT_WALLET_PASSWORD.getBytes()));
            userService.saveDuplicateNoThrow(user);
            loginUser = user;
        }

        return generateLoginToken(loginUser, tokenCacheKey, noOperationTimeOut);
    }

    @Override
    public String hasLogin(LoginInDTO loginInDTO)
    {
        return hasLogin(CacheKeyConstants.CACHE_EXIST_PREFIX + loginInDTO.getDomainId() + loginInDTO.getWalletAddress());
    }
}
