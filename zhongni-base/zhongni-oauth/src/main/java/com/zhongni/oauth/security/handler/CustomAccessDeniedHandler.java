package com.zhongni.oauth.security.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.zhongni.oauth.entity.resp.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
@Slf4j
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        log.error("Access Denied is Exception: {}", e.getMessage(), e);
        CommonResponse<String> errorResponse =  CommonResponse.resp(String.valueOf(HttpStatus.FORBIDDEN.value()),
                HttpStatus.FORBIDDEN.getReasonPhrase() + ": " + e.getMessage(), "");
        out.write(JSON.toJSONString(errorResponse, SerializerFeature.SortField));
        out.flush();
        out.close();
    }
}