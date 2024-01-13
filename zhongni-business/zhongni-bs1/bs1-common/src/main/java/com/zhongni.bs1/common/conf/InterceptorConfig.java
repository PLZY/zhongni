package com.zhongni.bs1.common.conf;

import com.alibaba.fastjson.JSON;
import com.zhongni.bs1.common.interceptor.GlobalInterceptor;
import com.zhongni.bs1.common.interceptor.LoginInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@Slf4j
public class InterceptorConfig implements WebMvcConfigurer {

    @Value("${exclude.check.url}")
    private String[] excludeUrls;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new GlobalInterceptor());
        log.info("exclude url array is : {}", JSON.toJSONString(excludeUrls));
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns(excludeUrls);
    }
}
