package com.zhongni.oauth.security.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class Oauth2SuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // 在这里自定义成功登录后的处理逻辑
        String redirectUrl = determineRedirectUrl(request);
        response.sendRedirect(redirectUrl);
    }

    private String determineRedirectUrl(HttpServletRequest request) {
        // 在这里从请求参数或数据库中获取重定向URL的逻辑
        String redirectUrl = request.getParameter("redirectUrl");

        // 如果请求参数中没有指定重定向URL，则从数据库中获取默认的重定向URL
        if (redirectUrl == null) {
            // 从数据库中获取默认的重定向URL，这里假设有一个方法获取数据库中的配置
            redirectUrl = getDefaultRedirectUrlFromDatabase();
        }

        return redirectUrl;
    }

    private String getDefaultRedirectUrlFromDatabase() {
        // 实际的数据库查询逻辑在这里实现
        // 返回数据库中配置的默认重定向URL
        return "https://www.baidu.com/";
    }
}
