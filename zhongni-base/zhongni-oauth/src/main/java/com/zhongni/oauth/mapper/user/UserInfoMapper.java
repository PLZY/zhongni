package com.zhongni.oauth.mapper.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhongni.oauth.config.DataSourceConfig;
import com.zhongni.oauth.entity.user.UserInfo;
import org.springframework.context.annotation.Import;

/**
* @author z1085
* @description 针对表【user_info】的数据库操作Mapper
* @createDate 2024-01-20 18:55:03
* @Entity user.entity.UserInfo
*/
public interface UserInfoMapper extends BaseMapper<UserInfo> {

}




