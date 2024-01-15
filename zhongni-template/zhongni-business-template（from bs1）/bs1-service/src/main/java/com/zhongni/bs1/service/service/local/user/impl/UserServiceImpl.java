package com.zhongni.bs1.service.service.local.user.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhongni.bs1.common.constants.BusinessConstants;
import com.zhongni.bs1.common.constants.CacheKeyConstants;
import com.zhongni.bs1.common.enums.BusinessExceptionEnum;
import com.zhongni.bs1.common.enums.LoginTypeEnum;
import com.zhongni.bs1.common.enums.UserStatusEnum;
import com.zhongni.bs1.common.enums.UserTypeEnum;
import com.zhongni.bs1.common.exception.BusinessException;
import com.zhongni.bs1.common.spring.AppContext;
import com.zhongni.bs1.common.util.CacheUtil;
import com.zhongni.bs1.common.util.LockUtil;
import com.zhongni.bs1.common.util.RandomUtil;
import com.zhongni.bs1.service.remote.RemoteRequest;
import com.zhongni.bs1.service.service.local.login.LoginService;
import com.zhongni.bs1.service.service.local.user.UserService;
import com.zhongni.bs1.service.service.local.verification.VerificationService;
import com.zhongni.bs1.entity.db.user.User;
import com.zhongni.bs1.entity.dto.user.*;
import com.zhongni.bs1.entity.other.DealImgUploadFileResultObj;
import com.zhongni.bs1.mapper.user.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
*/
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService {

    /**
     * 登录用户无操作多久后自动退出登录（单位：秒），默认10分钟
     */
    @Value("${no.operation.timeout:600}")
    private long noOperationTimeOut;

    /**
     * 图片上传的路径
     */
    @Value("${upload.img.path}")
    private String uploadImgPath;

    @Value("${img.file.type.support}")
    private String[] imgFileTypeSupportArray;

    @Autowired
    @Lazy
    private VerificationService verificationService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RemoteRequest remoteRequest;

    /**
     * 注册
     * @param useRregisterLocalInDTO 注册入参对象
     */
    @Override
    @Transactional
    public List<String> registerLocal(UseRregisterLocalInDTO useRregisterLocalInDTO)
    {
        checkBeforeRegisterLocal(useRregisterLocalInDTO);
        User user = combineNewUser(useRregisterLocalInDTO);
        // 先入表，获取mysql自增的用户id
        saveDuplicateNoThrow(user);
        JSONObject jsonObject = getWalletAddressFromRemoteApi(user);
        user.setWalletAddress(jsonObject.getString("wallet_address"));
        user.setUserStatus(UserStatusEnum.ENABLE.getCode());
        // 获取到新增用户id后再使用用户id命名用户头像进行上传
        user.setImagePath(getImgPathByInParams(useRregisterLocalInDTO, user));
        // 获取到用户的钱包地址之后，进行更新操作
        try
        {
            updateById(user);
        }
        catch (Exception e)
        {
            // 更新失败整个事务都会回滚，相应的如果用户头像已经上传也需要删除
            if(null != useRregisterLocalInDTO.getImgFile())
            {
                File f = new File(user.getImagePath());
                try
                {
                    Files.delete(f.toPath());
                }
                catch (IOException ex)
                {
                    // 此处删除失败就不抛异常了，只记录一下日志，后续新头像上传成功后会自动把当前文件覆盖掉
                    log.error("person img File delete fail path is {}, error info is {}", f.getAbsolutePath(), e.getMessage(), e);
                }
            }

            throw e;
        }

        List<String> worlds = Arrays.asList(jsonObject.getString("world").split(","));
        // 所有逻辑处理成功后删除缓存
        CacheUtil.del(CacheKeyConstants.CACHE_CHECK_PASS_PREFIX + useRregisterLocalInDTO.getEmailAddress());
        // 返回助记词
        return worlds;
    }

    /**
     * 根据入参返回头像路径
     * @param useRregisterLocalInDTO 入参对象
     * @param user 用户信息对象
     */
    private String getImgPathByInParams(UseRregisterLocalInDTO useRregisterLocalInDTO, User user)
    {
        // 如果上传的是系统默认头像直接返回对应路径即可
        if(StringUtils.isNotBlank(useRregisterLocalInDTO.getImgPath()))
        {
            if(null != useRregisterLocalInDTO.getImgFile())
            {
                throw new BusinessException(BusinessExceptionEnum.PARAMS_EXCEPTION_ONLY_ONE_TYPE_CAN_UPLOAD);
            }

            return useRregisterLocalInDTO.getImgPath();
        }

        // 如果既没有上传系统默认头像，也没有上传自定义头像则直接返回一个默认值
        if(null == useRregisterLocalInDTO.getImgFile())
        {
            return BusinessConstants.DEFAULT_IMAGE_PATH;
        }

        // 保存自定义头像并返回上传的路径
        return uploadImg(user.getUserId(), useRregisterLocalInDTO.getImgFile());
    }

    /**
     * 用户登录
     * @param loginInDTO 登录的入参对象
     */
    @Override
    public String login(LoginInDTO loginInDTO)
    {
        String tokenCacheKey = LoginTypeEnum.LOCAL.getCode().equals(loginInDTO.getLoginType()) ?
                CacheKeyConstants.CACHE_EXIST_PREFIX + loginInDTO.getEmailAddress() : CacheKeyConstants.CACHE_EXIST_PREFIX + loginInDTO.getDomainId() + loginInDTO.getWalletAddress();
        // 用户的登录逻辑需由一个线程进行执行完毕，缓存登录的token后，另外的线程才可进入执行，否则重复登录的判断逻辑可能会有问题
        return LockUtil.syncExecute(tokenCacheKey, loginInDTO, item -> {
            // 根据登录类型，获取不同的登录实现类
            LoginService loginService = AppContext.getBean(LoginTypeEnum.getImplClass(item.getLoginType()), LoginService.class);
            // 登录用户需要存储一下标识，用于判断用户是否重复登录。本网站登录的用户使用邮箱地址作为唯一key，三方登录则使用钱包区块链和钱包地址作为唯一key
            String hasLoginToken = loginService.hasLogin(item);
            // 已经登录过且没有失效直接返回当前缓存的token
            if(StringUtils.isNotBlank(hasLoginToken))
            {
                // 验证码登录时，一个验证码只允许使用一次，校验成功后会将此验证码删除，因此想使用一个已经校验过的验证码再次登录则直接报错，因此在此处再校验一下
                if(LoginTypeEnum.LOCAL.getCode().equals(item.getLoginType()) && StringUtils.isNotBlank(item.getVerificationCode()))
                {
                    verificationService.checkVerificationCode(
                            CacheKeyConstants.CACHE_LOGIN_PREFIX + item.getEmailAddress(), item.getVerificationCode());
                }

                return hasLoginToken;
            }

            // 删掉无效的缓存
            CacheUtil.del(tokenCacheKey);
            // 登录
            return loginService.login(item);
        });
    }

    /**
     * 用户注销
     * @param token 登录的标识
     */
    @Override
    public void logout(String token) {
        CacheUtil.del(CacheKeyConstants.CACHE_TOKEN_PREFIX + token);
    }

    /**
     * 密码重置
     * @param resetPasswordInDTO 密码重置入参对象
     */
    @Override
    public void resetPassword(ResetPasswordInDTO resetPasswordInDTO)
    {
        User user = Optional.ofNullable(CacheUtil.get(CacheKeyConstants.CACHE_CHECK_PASS_PREFIX + resetPasswordInDTO.getEmailAddress(), User.class)).
                orElseThrow(() -> new BusinessException(BusinessExceptionEnum.PARAMS_EXCEPTION_EMAIL_NOT_CHECKED_BY_RESET));
        if(!StringUtils.equals(user.getEmailAddress(), resetPasswordInDTO.getEmailAddress()))
        {
            throw new BusinessException(BusinessExceptionEnum.PARAMS_EXCEPTION_EMAIL_NOT_EQUALS);
        }

        // 验证成功之后需要将此key在缓存中删掉
        CacheUtil.del(CacheKeyConstants.CACHE_CHECK_PASS_PREFIX + resetPasswordInDTO.getEmailAddress());
        // 将新的密码放入当前登录用户信息中并进行修改
        user.setPassWord(resetPasswordInDTO.getPassWord());
        updateById(user);
    }

    /**
     * 根据登录的token获取用户
     * @param token 登录的标识
     */
    @Override
    public User getLoginUserByToken(String token) {
        return Optional.ofNullable(CacheUtil.get(CacheKeyConstants.CACHE_TOKEN_PREFIX + token, User.class)).orElseThrow(() -> new BusinessException(BusinessExceptionEnum.DATA_EXCEPTION_USER_CACHE_IS_NULL));
    }

    /**
     * 注册前校验
     * @param useRregisterLocalInDTO 用户本地注册入参对象
     */
    private void checkBeforeRegisterLocal(UseRregisterLocalInDTO useRregisterLocalInDTO)
    {
        String emailAddress = useRregisterLocalInDTO.getEmailAddress();
        if(!CacheKeyConstants.CACHE_CHECK_PASS_VALUE.equals(CacheUtil.get(CacheKeyConstants.CACHE_CHECK_PASS_PREFIX + emailAddress)))
        {
            throw new BusinessException(BusinessExceptionEnum.PARAMS_EXCEPTION_EMAIL_NOT_CHECKED_BY_REGISTER);
        }

        User exitUser = getOne(new LambdaQueryWrapper<User>().
                eq(User::getEmailAddress, emailAddress).
                eq(User::getUserStatus, UserStatusEnum.ENABLE.getCode()));
        if(null != exitUser)
        {
            throw new BusinessException(BusinessExceptionEnum.PARAMS_EXCEPTION_EMAIL_HAS_BEEN_REGISTERED, emailAddress);
        }
    }

    /**
     * 上传头像并返回上传的文件路径
     * @param userId 用户id
     * @param imgFile 文件对象
     */
    private String uploadImg(Long userId, MultipartFile imgFile)
    {
        // 包括类型后缀
        String fileName = imgFile.getOriginalFilename();
        if(StringUtils.isBlank(fileName))
        {
            throw new BusinessException(BusinessExceptionEnum.PARAMS_EXCEPTION_FILE_NAME_EMPTY);
        }

        long fileSize = imgFile.getSize();
        log.info("upload img info[file content type is {}, file name is {}, file size is {} Bit]", imgFile.getContentType(), fileName, fileSize);
        if(-1 == fileName.lastIndexOf("."))
        {
            throw new BusinessException(BusinessExceptionEnum.PARAMS_EXCEPTION_FILE_SUFFIX_EMPTY);
        }

        String fileTypeName = fileName.substring(fileName.lastIndexOf("."));
        if(!ArrayUtils.contains(imgFileTypeSupportArray, fileTypeName.toLowerCase()))
        {
            throw new BusinessException(BusinessExceptionEnum.PARAMS_EXCEPTION_UNSUPPORTED_IMAGE_TYPE);
        }

        if (fileSize > BusinessConstants.MAX_IMG_FILE_SIZE) {
            throw new BusinessException(BusinessExceptionEnum.PARAMS_EXCEPTION_IMAGE_TOO_LARGE, "Maximum allowable size is " + (BusinessConstants.MAX_IMG_FILE_SIZE / 1024 / 1024) + " MB");
        }

        String uploadPath = uploadImgPath + File.separator + userId + fileTypeName;
        log.info("uploadImg File Path is {}", uploadPath);
        File f = new File(uploadPath);
        if(!f.exists() && !f.mkdirs())
        {
            throw new BusinessException(BusinessExceptionEnum.SYSTEM_EXCEPTION_UPLOAD_PATH_CREATE_FAIL);
        }

        try
        {
            imgFile.transferTo(f);
        }
        catch (IOException e)
        {
            log.error("UserServiceImpl.uploadImg is IOException {}", e.getMessage(), e);
            throw new BusinessException(BusinessExceptionEnum.SYSTEM_EXCEPTION_UPLOAD_IMG_FAIL);
        }

        return uploadPath;
    }

    /**
     * 远程获取钱包地址
     * @param user 用户对象
     */
    private JSONObject getWalletAddressFromRemoteApi(User user)
    {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, Object> jsonMap = new LinkedMultiValueMap<>();
        jsonMap.add("user_id", user.getUserId());
        jsonMap.add("blockchain_id", user.getDomainId());
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(jsonMap, headers);
        log.info("request httpEntity is {}", JSON.toJSONString(httpEntity));
        String strbody = restTemplate.postForEntity(remoteRequest.getCreateWalletUrl(), httpEntity, String.class).getBody();
        log.info("getWalletAddressFromRemoteApi response is {}", strbody);
        JSONObject jsonObject = JSON.parseObject(strbody);
        if(!"200".equals(jsonObject.getString("code")))
        {
            throw new BusinessException(BusinessExceptionEnum.REMOTE_EXCEPTION, jsonObject.getString("message"));
        }

        return jsonObject.getJSONObject("data");
    }

    /**
     * 更新用户基本信息
     * @param userInDTO 用户信息入参对象
     */
    @Override
    @Transactional
    public void updateBase(UserInDTO userInDTO)
    {
        // 一个用户的修改同一时间只能由一根线程进行操作，需保证第一次修改成功后，下一次的修改才能进行，否则可能导致用户缓存信息覆盖或者邀请码判断有问题
        LockUtil.syncExecute(userInDTO.getToken(), userInDTO, item -> {
            User loginUser = getLoginUserByToken(item.getToken());
            checkBeforeUpdateUserBase(loginUser, item);
            User loginUserCopy = JSON.parseObject(JSON.toJSONString(loginUser), User.class);
            loginUserCopy.setInviterInvitationCode(StringUtils.defaultIfBlank(item.getInvitationCode(), loginUserCopy.getInviterInvitationCode()));
            loginUserCopy.setNickName(StringUtils.defaultIfBlank(item.getNickName(), loginUserCopy.getNickName()));
            // 如果头像信息为空，直接将数据更新入表即可
            if(StringUtils.isBlank(item.getImgPath()) && null == item.getImgFile())
            {
                updateById(loginUserCopy);
                // 用户更新成功之后，更新缓存
                CacheUtil.put(CacheKeyConstants.CACHE_TOKEN_PREFIX + item.getToken(), loginUserCopy, noOperationTimeOut, TimeUnit.SECONDS);
                return;
            }

            // 上传的是系统默认头像
            if(StringUtils.isNotBlank(item.getImgPath()))
            {
                update2SystemDefaultImgePath(loginUserCopy, item);
                updateById(loginUserCopy);
                // 用户更新成功之后，更新缓存
                CacheUtil.put(CacheKeyConstants.CACHE_TOKEN_PREFIX + item.getToken(), loginUserCopy, noOperationTimeOut, TimeUnit.SECONDS);
                return;
            }

            // 处理自定义头像上传并更新用户数据
            dealCustImgUploadAndUpdateUser(loginUser, loginUserCopy, item);
        });
    }

    /**
     * 更新成系统默认头像地址
     * @param loginUserCopy 登录用户的副本
     * @param userInDTO 入参对象
     */
    private void update2SystemDefaultImgePath(User loginUserCopy, UserInDTO userInDTO)
    {
        // 不能同时上传系统头像和自定义头像
        if(null != userInDTO.getImgFile())
        {
            throw new BusinessException(BusinessExceptionEnum.PARAMS_EXCEPTION_ONLY_ONE_TYPE_CAN_UPLOAD);
        }

        // 用户之前无头像且这次修改的是系统的一些默认头像，直接放入
        if(StringUtils.isBlank(loginUserCopy.getImagePath()))
        {
            loginUserCopy.setImagePath(userInDTO.getImgPath());
            return;
        }

        // 用户之前是系统默认头像且这次修改的还是系统的一些默认头像，也直接放入
        if(loginUserCopy.getImagePath().startsWith(BusinessConstants.DEFAULT_IMG_PATH_PREFIX))
        {
            loginUserCopy.setImagePath(userInDTO.getImgPath());
            return;
        }

        // 用户之前是自定义头像，这次修改成了系统默认头像
        File f = new File(loginUserCopy.getImagePath());
        try
        {
            // 先删掉之前上传的自定义图片
            Files.delete(f.toPath());
        }
        catch (IOException e)
        {
            log.error("delete user img fail, file path is {}, {}", loginUserCopy.getImagePath(), e.getMessage(), e);
            throw new BusinessException(BusinessExceptionEnum.SYSTEM_EXCEPTION_IMAGE_FILE_DELETE_FAIL);
        }

        // 删除旧头像之后将本次默认头像路径放入
        loginUserCopy.setImagePath(userInDTO.getImgPath());
    }

    /**
     * 处理自定义头像上传并更新用户数据
     * @param loginUser 登录用户
     * @param loginUserCopy 登录用户拷贝的副本
     * @param userInDTO 入参对象
     */
    private void dealCustImgUploadAndUpdateUser(User loginUser, User loginUserCopy, UserInDTO userInDTO)
    {
        // 上传了自定义头像则先进行图片上传处理
        DealImgUploadFileResultObj dealImgUploadFileResultObj = dealUserImgFile(loginUserCopy, userInDTO.getImgFile());
        // 将新图片上传并将上传后的头像地址放入
        loginUserCopy.setImagePath(StringUtils.defaultIfBlank(dealImgUploadFileResultObj.getUploadFilePath(), loginUserCopy.getImagePath()));
        try
        {
            // 更新失败的话需要回滚头像，此处用try catch包裹一下
            updateById(loginUserCopy);
        }
        catch (Exception e)
        {
            // 更新失败回滚为旧头像文件
            rollBackImgFileIfUpdateFail(dealImgUploadFileResultObj);
            throw e;
        }

        // 更新成功删除备份冗余的旧头像
        deleteRedundancyImgFileAfterUpdateSuccess(dealImgUploadFileResultObj.getCopyImgFile());
        // 用户更新成功之后，更新缓存
        CacheUtil.put(CacheKeyConstants.CACHE_TOKEN_PREFIX + userInDTO.getToken(), loginUserCopy, noOperationTimeOut, TimeUnit.SECONDS);
    }

    /**
     * 更新用户信息的事前校验
     * @param loginUser 登录用户对象
     * @param userInDTO 用户修改入参对象
     */
    private void checkBeforeUpdateUserBase(User loginUser, UserInDTO userInDTO)
    {
        // 如果当前用户已经存在了邀请人的钱包地址证明之前已经维护过邀请码，邀请码只允许填写一次
        if(StringUtils.isNotBlank(userInDTO.getInvitationCode()) && StringUtils.isNotBlank(loginUser.getInviterInvitationCode()))
        {
            throw new BusinessException(BusinessExceptionEnum.USER_EXCEPTION_IVNITATION_CODE_CANNOT_EDIT_IF_EXIST);
        }
    }

    /**
     * 处理用户头像文件
     * @param loginUserCopy 登录用户的拷贝对象
     * @param imgFile 头像文件
     */
    private DealImgUploadFileResultObj dealUserImgFile(User loginUserCopy, MultipartFile imgFile)
    {
        // 旧文件对象
        File oldImgFile = null;
        // 更换头像时需要先将旧文件对象备份一下
        File copyImgFile = null;
        // 如果之前已经有头像了且不是系统默认头像则先将旧文件存储之后再上传新文件
        if(StringUtils.isNotBlank(loginUserCopy.getImagePath()) && !loginUserCopy.getImagePath().startsWith(BusinessConstants.DEFAULT_IMG_PATH_PREFIX))
        {
            oldImgFile = new File(loginUserCopy.getImagePath());
            copyImgFile = new File(oldImgFile.getPath() + System.currentTimeMillis());
            if(!oldImgFile.renameTo(copyImgFile))
            {
                log.error("copy old image [{}] to [{}] fail", oldImgFile.getPath(), copyImgFile.getPath());
                throw new BusinessException(BusinessExceptionEnum.SYSTEM_EXCEPTION_BACKUP_IMAGE_FAIL);
            }
        }

        return new DealImgUploadFileResultObj(uploadImg(loginUserCopy.getUserId(), imgFile), oldImgFile, copyImgFile);
    }

    /**
     * 更新用户信息异常后的头像文件回滚
     * @param dealImgUploadFileResultObj 处理头像上传的结果对象
     */
    private void rollBackImgFileIfUpdateFail(DealImgUploadFileResultObj dealImgUploadFileResultObj)
    {
        File copyImgFile = dealImgUploadFileResultObj.getCopyImgFile();
        File oldImgFile = dealImgUploadFileResultObj.getOldImgFile();
        if(null == copyImgFile)
        {
            return;
        }

        // 数据更新失败，已经上传的修改后的头像需要改回去
        try
        {
            // 目前头像文件名使用的是用户id，不管头像怎么变但是文件路径一直是一个，所以此时旧文件对象的的路径现在指向的就是新上传的头像文件，直接使用此对象删除即可
            Files.delete(oldImgFile.toPath());
        }
        catch (IOException ex)
        {
            log.error("delete new img file fail, file path is {}, {}", oldImgFile.getPath(), ex.getMessage(), ex);
            throw new BusinessException(BusinessExceptionEnum.SYSTEM_EXCEPTION_IMAGE_FILE_DELETE_FAIL);
        }

        if(!copyImgFile.renameTo(oldImgFile))
        {
            log.error("roll back old img file fail, copy file path is {}, old file path is {}", copyImgFile.getPath(),
                    oldImgFile.getPath());
        }
    }

    /**
     * 更新成功后删除冗余的备份头像
     * @param copyImgFile 头像备份文件
     */
    private void deleteRedundancyImgFileAfterUpdateSuccess(File copyImgFile)
    {
        if(null == copyImgFile)
        {
            return;
        }

        try
        {
            // 修改成功后将之前备份的旧头像删除
            Files.delete(copyImgFile.toPath());
        }
        catch (IOException e)
        {
            log.error("delete copy file fail path is {}, {}", copyImgFile.getPath(), e.getMessage(), e);
            throw new BusinessException(BusinessExceptionEnum.SYSTEM_EXCEPTION_IMAGE_FILE_DELETE_FAIL);
        }
    }

    @Override
    public void walletpwdUpdate(String token, WalletPasswordInDTO walletPasswordInDTO) {
        if(StringUtils.isBlank(walletPasswordInDTO.getOldWalletPassword()))
        {
            // 没有旧密码走密码重置逻辑
            log.info("密码重置逻辑....");
        }

        log.info("密码修改逻辑....");
    }

    /**
     * 组装用户信息
     * @param useRregisterLocalInDTO 用户本地注册入参对象
     */
    @Override
    public User combineNewUser(UseRregisterLocalInDTO useRregisterLocalInDTO) {
        User user = new User();
        user.setDomainId(useRregisterLocalInDTO.getDomainId());
        // todo 钱包地址有唯一索引，为防止重复，先使用uuid进行占位，获取到真正的钱包地址之后再修改,目前的接口是同步做完所有业务后才会提交事务性能较低，日后可能需要优化
        //user.setWalletAddress(UUID.randomUUID().toString());
        user.setNickName(StringUtils.defaultIfBlank(useRregisterLocalInDTO.getNickName(), "USER_" + System.nanoTime()));
        user.setPassWord(useRregisterLocalInDTO.getPassWord());
        user.setWalletPassword(useRregisterLocalInDTO.getWalletPassword());
        // 先统一放成LOCAL用户，三方用户登录时会将该类型覆盖成THIRD用户
        user.setUserType(UserTypeEnum.LOCAL.getCode());
        // 生成用户本人的邀请码并放入
        user.setOwnInvitationCode(RandomUtil.generateInvitationCode());
        // 如果当前用户注册时放了邀请码则需放置邀请人的邀请码
        user.setInviterInvitationCode(useRregisterLocalInDTO.getInvitationCode());
        user.setEmailAddress(useRregisterLocalInDTO.getEmailAddress());
        return user;
    }

    /**
     * 用户新增
     * @param user 用户信息
     */
    @Override
    public void saveDuplicateNoThrow(User user)
    {
        try
        {
            // 先入库，获取mysql的自增ID
            save(user);
        }
        catch (DuplicateKeyException e)
        {
            if(Objects.requireNonNull(e.getMessage()).contains("squid_user.idx_unique_invitation"))
            {
                // 经测试邀请码在大数据量下可能存在重复值，为保险起见此处进行处理一下，若发现重复邀请码重新生成后再次尝试新增
                user.setOwnInvitationCode(RandomUtil.generateInvitationCode());
                saveDuplicateNoThrow(user);
                return;
            }

            // 其他重复数据异常直接抛出
            throw e;
        }
    }
}

