package com.zhongni.oauth.security.handler;

import com.zhongni.oauth.jwt.JwtTokenProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Resource
    private JwtTokenProvider jwtTokenProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String token = jwtTokenProvider.generateToken(authentication);
        response.addHeader("Authorization", "Bearer " + token);
    }
}