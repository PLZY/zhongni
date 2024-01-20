package com.zhongni.oauth.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhongni.oauth.constants.OauthConstants;
import com.zhongni.oauth.entity.resp.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
@Slf4j
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        log.error("Auth is failure : {}", e.getMessage(), e);
        CommonResponse<String> errorResponse =  CommonResponse.resp(OauthConstants.DEFAULT_ERROR_CODE, e.getMessage(), "");
        out.write(objectMapper.writeValueAsString(errorResponse));
        out.flush();
        out.close();
    }
}