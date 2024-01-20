package com.zhongni.oauth.service.auth.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhongni.oauth.entity.auth.UserRole;
import com.zhongni.oauth.mapper.auth.UserRoleMapper;
import com.zhongni.oauth.service.auth.UserRoleService;
import org.springframework.stereotype.Service;

/**
* @author z1085
* @description 针对表【user_role】的数据库操作Service实现
* @createDate 2024-01-20 19:25:14
*/
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole>
    implements UserRoleService {

}




