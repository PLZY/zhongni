package com.zhongni.oauth.service.auth.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhongni.oauth.entity.auth.RoleAuth;
import com.zhongni.oauth.mapper.auth.RoleAuthMapper;
import com.zhongni.oauth.service.auth.RoleAuthService;
import org.springframework.stereotype.Service;

/**
* @author z1085
* @description 针对表【role_auth】的数据库操作Service实现
* @createDate 2024-01-20 19:25:04
*/
@Service
public class RoleAuthServiceImpl extends ServiceImpl<RoleAuthMapper, RoleAuth>
    implements RoleAuthService {

}




