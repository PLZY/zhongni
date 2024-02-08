package com.zhongni.oauth.security.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
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

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        log.error("Auth is failure : {}", e.getMessage(), e);
        CommonResponse<String> errorResponse =  CommonResponse.resp(String.valueOf(HttpStatus.UNAUTHORIZED.value()),
                HttpStatus.UNAUTHORIZED.getReasonPhrase() + ": " + e.getMessage(), "");
        out.write(JSON.toJSONString(errorResponse, SerializerFeature.SortField));
        out.flush();
        out.close();
    }
}