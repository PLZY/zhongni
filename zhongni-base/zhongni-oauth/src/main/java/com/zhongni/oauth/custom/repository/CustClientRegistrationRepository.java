package com.zhongni.oauth.custom.repository;

import com.zhongni.oauth.entity.client.ClientInfo;
import com.zhongni.oauth.service.client.ClientInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class CustClientRegistrationRepository implements ClientRegistrationRepository {

    private final Map<String, ClientRegistration> clients = new HashMap<>();

    private final ClientInfoService clientInfoService;

    public CustClientRegistrationRepository(ClientInfoService clientInfoService) {
        // 初始化加载数据库中的客户端信息
        this.clientInfoService = clientInfoService;
        loadClientRegistrationsFromDatabase();
    }

    private void loadClientRegistrationsFromDatabase() {
        List<ClientInfo> list = clientInfoService.list();
        list.forEach(this::addRegistration);
    }

    @Override
    public ClientRegistration findByRegistrationId(String registrationId) {
        return clients.get(registrationId);
    }

    public void addRegistration(ClientInfo clientInfo) {
        // 根据数据库中的实体类创建ClientRegistration对象并添加到clients Map中
        ClientRegistration clientRegistration = ClientRegistration.withRegistrationId(clientInfo.getClientId())
                .clientId(clientInfo.getClientId())
                .clientSecret(clientInfo.getClientSecret())
                .redirectUri(clientInfo.getRedirectUri())
                .scope(clientInfo.getClientScope())
                .authorizationGrantType(new AuthorizationGrantType(clientInfo.getGrantType()))
                .authorizationUri("https://www.baidu.com")
                .tokenUri("https://www.baidu.com")
                .build();
        clients.put(clientRegistration.getRegistrationId(), clientRegistration);
    }
}
