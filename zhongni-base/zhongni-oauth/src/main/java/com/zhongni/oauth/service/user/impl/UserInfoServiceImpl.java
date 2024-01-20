package com.zhongni.oauth.service.user.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhongni.oauth.config.DataSourceConfig;
import com.zhongni.oauth.entity.user.UserInfo;
import com.zhongni.oauth.mapper.user.UserInfoMapper;
import com.zhongni.oauth.service.user.UserInfoService;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Service;

/**
* @author z1085
* @description 针对表【user_info】的数据库操作Service实现
* @createDate 2024-01-20 18:55:03
*/
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo>
    implements UserInfoService {

}




