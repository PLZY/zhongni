package com.zhongni.oauth.service.client.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhongni.oauth.entity.client.ClientInfo;
import com.zhongni.oauth.mapper.client.ClientInfoMapper;
import com.zhongni.oauth.service.client.ClientInfoService;
import org.springframework.stereotype.Service;

/**
* @author z1085
* @description 针对表【client_info(三方应用信息)】的数据库操作Service实现
* @createDate 2024-01-26 15:15:35
*/
@Service
public class ClientInfoServiceImpl extends ServiceImpl<ClientInfoMapper, ClientInfo>
    implements ClientInfoService {
}




