package com.zhongni.oauth.service.auth.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhongni.oauth.entity.auth.AuthInfo;
import com.zhongni.oauth.mapper.auth.AuthInfoMapper;
import com.zhongni.oauth.service.auth.AuthInfoService;
import org.springframework.stereotype.Service;

/**
* @author z1085
* @description 针对表【auth_info】的数据库操作Service实现
* @createDate 2024-01-20 19:24:50
*/
@Service
public class AuthInfoServiceImpl extends ServiceImpl<AuthInfoMapper, AuthInfo>
    implements AuthInfoService {

}




