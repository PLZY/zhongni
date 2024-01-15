package com.zhongni.bs1.service.service.local.login.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zhongni.bs1.common.constants.CacheKeyConstants;
import com.zhongni.bs1.common.enums.BusinessExceptionEnum;
import com.zhongni.bs1.common.enums.UserStatusEnum;
import com.zhongni.bs1.common.exception.BusinessException;
import com.zhongni.bs1.common.util.CacheUtil;
import com.zhongni.bs1.service.service.local.login.LoginService;
import com.zhongni.bs1.service.service.local.user.UserService;
import com.zhongni.bs1.service.service.local.verification.VerificationService;
import com.zhongni.bs1.entity.db.user.User;
import com.zhongni.bs1.entity.dto.user.LoginInDTO;
import com.zhongni.bs1.entity.other.VerificationObj;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service("localLoginServiceImpl")
public class LocalLoginServiceImpl implements LoginService {

    @Autowired
    private UserService userService;

    @Autowired
    private VerificationService verificationService;

    /**
     * 登录用户无操作多久后自动退出登录（单位：秒），默认10分钟
     */
    @Value("${no.operation.timeout:600}")
    private long noOperationTimeOut;

    /**
     * 本地登录
     * @param loginInDTO 登录入参对象
     */
    @Override
    public String login(LoginInDTO loginInDTO)
    {
        if(StringUtils.isBlank(loginInDTO.getEmailAddress()))
        {
            throw new BusinessException(BusinessExceptionEnum.PARAMS_EXCEPTION_EMAIL_ADDRESS_IS_NULL);
        }

        String tokenCacheKey = CacheKeyConstants.CACHE_EXIST_PREFIX + loginInDTO.getEmailAddress();
        // 验证码不是空直接进行验证码登录，不再判断密码
        if(StringUtils.isNotBlank(loginInDTO.getVerificationCode()))
        {
            String cacheKey = CacheKeyConstants.CACHE_LOGIN_PREFIX + loginInDTO.getEmailAddress();
            verificationService.checkVerificationCode(cacheKey, loginInDTO.getVerificationCode());
            VerificationObj verificationObj = CacheUtil.get(cacheKey, VerificationObj.class);
            // 验证成功之后需要将此key在缓存中删掉
            CacheUtil.del(cacheKey);
            User loginUser = verificationObj.getUser();
            loginUser.setLastLoginTime(new Date());
            // 更新一下最后一次的登录时间
            userService.updateById(loginUser);
            return generateLoginToken(loginUser, tokenCacheKey, noOperationTimeOut);
        }

        User loginUser = Optional.ofNullable(userService.getOne(new LambdaQueryWrapper<User>().eq(User::getEmailAddress, loginInDTO.getEmailAddress())
                .eq(User::getUserStatus, UserStatusEnum.ENABLE.getCode())
                .eq(User::getUserType, loginInDTO.getLoginType()))).orElseThrow(() ->
                new BusinessException(BusinessExceptionEnum.DATA_EXCEPTION_CAN_NOT_GET_USER));
        if(StringUtils.isBlank(loginInDTO.getPassWord()))
        {
            throw new BusinessException(BusinessExceptionEnum.PARAMS_EXCEPTION_LOGIN_PASSWORD_IS_NULL);
        }

        if(!loginInDTO.getPassWord().equals(loginUser.getPassWord()))
        {
            throw new BusinessException(BusinessExceptionEnum.PARAMS_EXCEPTION_LOGIN_PASSWORD_ERROR);
        }

        loginUser.setLastLoginTime(new Date());
        // 更新一下最后一次的登录时间
        userService.updateById(loginUser);
        return generateLoginToken(loginUser, tokenCacheKey, noOperationTimeOut);
    }

    @Override
    public String hasLogin(LoginInDTO loginInDTO)
    {
        return hasLogin(CacheKeyConstants.CACHE_EXIST_PREFIX + loginInDTO.getEmailAddress());
    }
}
