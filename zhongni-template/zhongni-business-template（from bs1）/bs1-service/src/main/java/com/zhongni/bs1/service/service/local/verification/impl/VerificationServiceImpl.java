package com.zhongni.bs1.service.service.local.verification.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zhongni.bs1.common.constants.CacheKeyConstants;
import com.zhongni.bs1.common.enums.BusinessExceptionEnum;
import com.zhongni.bs1.common.enums.MailTemplateContentEnum;
import com.zhongni.bs1.common.enums.UserStatusEnum;
import com.zhongni.bs1.common.exception.BusinessException;
import com.zhongni.bs1.common.util.CacheUtil;
import com.zhongni.bs1.common.util.RandomUtil;
import com.zhongni.bs1.service.service.local.mail.MailService;
import com.zhongni.bs1.service.service.local.user.UserService;
import com.zhongni.bs1.service.service.local.verification.VerificationService;
import com.zhongni.bs1.entity.db.user.User;
import com.zhongni.bs1.entity.dto.user.VerificationInDTO;
import com.zhongni.bs1.entity.other.VerificationObj;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class VerificationServiceImpl implements VerificationService {

    /**
     * 验证码验证通过之后成功标识位的缓存时间（单位：秒），默认10分钟
     */
    @Value("${check.success.timeout:600}")
    private long checkSuccessTimeOut;

    @Autowired
    private MailService mailService;

    @Autowired
    private UserService userService;

    /**
     * 验证码发送&验证码重发&验证码验证
     * @param verificationInDTO 验证入参对象
     */
    @Override
    public void verification(VerificationInDTO verificationInDTO)
    {
        // 验证码不是空时为校验验证码是否正确
        if(StringUtils.isNotBlank(verificationInDTO.getVerificationCode()))
        {
            checkVerificationCodeByType(verificationInDTO);
            return;
        }

        String emailAddress = verificationInDTO.getEmailAddress();
        User user = userService.getOne(new LambdaQueryWrapper<User>().
                eq(User::getEmailAddress, emailAddress).
                eq(User::getUserStatus, UserStatusEnum.ENABLE.getCode()));
        if(MailTemplateContentEnum.LOGIN.getCode().equals(verificationInDTO.getMailType()))
        {
            Optional.ofNullable(user).orElseThrow(() -> new BusinessException(BusinessExceptionEnum.PARAMS_EXCEPTION_EMAIL_NOT_REGISTERED, verificationInDTO.getEmailAddress()));
            // 发送验证码邮件到邮箱
            sendEmail(verificationInDTO, CacheKeyConstants.CACHE_LOGIN_PREFIX + emailAddress, user);
            return;
        }

        if(MailTemplateContentEnum.RESET.getCode().equals(verificationInDTO.getMailType()))
        {
            Optional.ofNullable(user).orElseThrow(() -> new BusinessException(BusinessExceptionEnum.PARAMS_EXCEPTION_EMAIL_NOT_REGISTERED, verificationInDTO.getEmailAddress()));
            // 发送验证码邮件到邮箱
            sendEmail(verificationInDTO, CacheKeyConstants.CACHE_RESET_PREFIX + emailAddress, user);
            return;
        }

        if(MailTemplateContentEnum.REGISTER.getCode().equals(verificationInDTO.getMailType()))
        {
            if(null != user)
            {
                throw new BusinessException(BusinessExceptionEnum.PARAMS_EXCEPTION_EMAIL_ADDRESS_EXIST, emailAddress);
            }

            // 验证码是纯随机的六位数字，可能会有重复，不能使用验证码直接当key，且后续还需要校验重复请求验证码，用邮箱当key比较实现简单
            // 发送验证码邮件到邮箱
            sendEmail(verificationInDTO, CacheKeyConstants.CACHE_VERIFICATION_PREFIX + emailAddress, null);
            return;
        }

        throw new BusinessException(BusinessExceptionEnum.PARAMS_EXCEPTION_UNSUPPORTED_MAIL_OPER_TYPE);
    }

    /**
     * 校验验证码的正确性
     * @param cacheKey 缓存的键
     * @param inputVerificationCode 输入的验证码
     */
    @Override
    public void checkVerificationCode(String cacheKey, String inputVerificationCode)
    {
        // 否则是校验验证码是否正确
        VerificationObj verificationObj = Optional.ofNullable(CacheUtil.get(cacheKey, VerificationObj.class)).
                orElseThrow(() -> new BusinessException(BusinessExceptionEnum.PARAMS_EXCEPTION_VERIFICATION_CODE_HAS_EXPIRED, inputVerificationCode));
        if(!StringUtils.equals(verificationObj.getVerificationCode().toUpperCase(), inputVerificationCode.trim().toUpperCase()))
        {
            throw new BusinessException(BusinessExceptionEnum.PARAMS_EXCEPTION_VERIFICATION_CODE_NOT_RIGHT, inputVerificationCode);
        }
    }

    /**
     * 邮件发送
     * @param verificationInDTO 入参对象
     * @param cacheKey 缓存key
     * @param user 用户信息
     */
    private void sendEmail(VerificationInDTO verificationInDTO, String cacheKey, User user)
    {
        if(null != CacheUtil.get(cacheKey))
        {
            throw new BusinessException(BusinessExceptionEnum.OPERATE_EXCEPTION_FREQUENT_SEND_EMAIL_REQUEST);
        }

        String verificationCode = RandomUtil.generateVerificationCode();
        mailService.sendHtmlMail(verificationInDTO.getEmailAddress(), verificationCode, verificationInDTO.getMailType());
    }

    /**
     * 根据类型对验证码进行不同的处理
     * @param verificationInDTO 验证入参对象
     */
    private void checkVerificationCodeByType(VerificationInDTO verificationInDTO)
    {
        if(MailTemplateContentEnum.LOGIN.getCode().equals(verificationInDTO.getMailType()))
        {
            throw new BusinessException(BusinessExceptionEnum.OPERATE_EXCEPTION_LOGIN_NOT_CHECK_IN_HERE);
        }

        if(MailTemplateContentEnum.RESET.getCode().equals(verificationInDTO.getMailType()))
        {
            checkVerificationCode(CacheKeyConstants.CACHE_RESET_PREFIX + verificationInDTO.getEmailAddress(), verificationInDTO.getVerificationCode());
            VerificationObj verificationObj = CacheUtil.get(CacheKeyConstants.CACHE_RESET_PREFIX + verificationInDTO.getEmailAddress(), VerificationObj.class);
            // 验证成功之后需要将此key在缓存中删掉
            CacheUtil.del(CacheKeyConstants.CACHE_RESET_PREFIX + verificationInDTO.getEmailAddress());
            // 放置当前邮箱校验通过标识
            CacheUtil.put(CacheKeyConstants.CACHE_CHECK_PASS_PREFIX + verificationInDTO.getEmailAddress(), verificationObj.getUser(), checkSuccessTimeOut, TimeUnit.SECONDS);
            return;
        }

        checkVerificationCode(CacheKeyConstants.CACHE_VERIFICATION_PREFIX + verificationInDTO.getEmailAddress(), verificationInDTO.getVerificationCode());
        // 验证成功之后需要将此key在缓存中删掉
        CacheUtil.del(CacheKeyConstants.CACHE_VERIFICATION_PREFIX + verificationInDTO.getEmailAddress());
        // 放置当前邮箱校验通过标识
        CacheUtil.put(CacheKeyConstants.CACHE_CHECK_PASS_PREFIX + verificationInDTO.getEmailAddress(), CacheKeyConstants.CACHE_CHECK_PASS_VALUE, checkSuccessTimeOut, TimeUnit.SECONDS);
    }
}
