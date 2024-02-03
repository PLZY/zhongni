package com.zhongni.oauth.service.login;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zhongni.oauth.entity.client.ClientInfo;
import com.zhongni.oauth.entity.login.CustClientInfo;
import com.zhongni.oauth.service.client.ClientInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class DBClientDetailsService implements ClientDetailsService {

    @Resource
    private ClientInfoService clientInfoService;

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        ClientInfo clientInfo = clientInfoService.getOne(new LambdaQueryWrapper<ClientInfo>().eq(ClientInfo::getClientId, clientId));
        if (clientInfo == null) {
            throw new ClientRegistrationException("Client not found: " + clientId);
        }
        CustClientInfo custClientInfo = new CustClientInfo();
        BeanUtils.copyProperties(clientInfo, custClientInfo);
        return custClientInfo.toClientDetails();
    }
}
