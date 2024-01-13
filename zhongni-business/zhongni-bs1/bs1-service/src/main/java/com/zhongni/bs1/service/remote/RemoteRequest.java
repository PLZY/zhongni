package com.zhongni.bs1.service.remote;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RemoteRequest {
    @Value("${remote.create.wallet.url}")
    private String createWalletUrl;
    @Value("${remote.request.ip}")
    private String requestIp;

    @Value("${remote.request.port}")
    private String requestPort;

    public String getCreateWalletUrl() {
        String realUrl = String.format(createWalletUrl, requestIp + ":" + requestPort);
        log.info("realUrl is {}", realUrl);
        return realUrl;
    }
}
