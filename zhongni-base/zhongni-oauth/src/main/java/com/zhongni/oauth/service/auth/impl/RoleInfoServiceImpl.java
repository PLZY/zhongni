package com.zhongni.oauth.service.auth.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhongni.oauth.entity.auth.RoleInfo;
import com.zhongni.oauth.mapper.auth.RoleInfoMapper;
import com.zhongni.oauth.service.auth.RoleInfoService;
import org.springframework.stereotype.Service;

/**
* @author z1085
* @description 针对表【role_info】的数据库操作Service实现
* @createDate 2024-01-20 19:25:10
*/
@Service
public class RoleInfoServiceImpl extends ServiceImpl<RoleInfoMapper, RoleInfo>
    implements RoleInfoService {

}




