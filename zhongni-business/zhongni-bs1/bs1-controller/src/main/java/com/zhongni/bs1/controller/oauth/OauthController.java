package com.zhongni.bs1.controller.oauth;

import com.alibaba.fastjson.JSONObject;
import com.zhongni.bs1.common.constants.BusinessConstants;
import com.zhongni.bs1.common.enums.BusinessExceptionEnum;
import com.zhongni.bs1.common.exception.BusinessException;
import com.zhongni.bs1.common.resp.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@RestController
@RequestMapping("/oauth")
@Slf4j
public class OauthController {

    @Value("${grant.type}")
    private String grantType;

    @Value("${client.id}")
    private String clientId;

    @Value("${client.secret}")
    private String clientSecret;

    @Value("${redirect.uri}")
    private String redirectUri;

    @Value("${oauth.check.uri}")
    private String oauthCheckUri;

    @GetMapping("/token")
    public CommonResponse<JSONObject> token(@RequestParam("code") String code) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(oauthCheckUri + "?grant_type=" + grantType +
                        "&code=" + code + "&client_id=" + clientId +
                        "&client_secret=" + clientSecret + "&redirect_uri=" + redirectUri))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if(!BusinessConstants.NODE_DEFAULT_SUCCESS_CODE.equals(String.valueOf(response.statusCode())))
        {
            throw new BusinessException(BusinessExceptionEnum.UNKNOWN_EXCEPTION, response.body());
        }

        return CommonResponse.resp(String.valueOf(response.statusCode()), "", JSONObject.parseObject(response.body()));
    }
}
