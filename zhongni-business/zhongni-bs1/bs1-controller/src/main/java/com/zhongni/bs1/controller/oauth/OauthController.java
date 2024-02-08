package com.zhongni.bs1.controller.oauth;

import com.zhongni.bs1.common.resp.CommonResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@RestController
@RequestMapping("/oauth")
public class OauthController {

    @Value("${grant.type}")
    private String grantType;

    @Value("${client.id}")
    private String clientId;

    @Value("${client.secret}")
    private String clientSecret;

    @Value("${redirect.uri}")
    private String redirectUri;

    @GetMapping("/token")
    public CommonResponse<String> token(@RequestParam("code") String code)
    {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:18082/oauth/oauth/token?grant_type=" + grantType +
                        "&code=" + code + "&client_id=" + clientId +
                        "&client_secret=" + clientSecret + "&redirect_uri=" + redirectUri))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("Response Code: " + response.statusCode());
            System.out.println("Response Body: " + response.body());
            return CommonResponse.success(response.body());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
