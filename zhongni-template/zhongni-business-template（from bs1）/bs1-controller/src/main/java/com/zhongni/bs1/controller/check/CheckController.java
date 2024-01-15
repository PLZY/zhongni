package com.zhongni.bs1.controller.check;

import com.alibaba.fastjson.JSONObject;
import com.zhongni.bs1.common.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 用于网关检测当前服务是否健康的接口
 */
@RestController
@Slf4j
@RequestMapping("/health")
public class CheckController {

    @Value("${spring.application.name}")
    private String applicationName;
    @Value("${server.port}")
    private String applicationPort;

    @GetMapping("/check")
    public JSONObject check()
    {
        String zc = RandomUtil.generateOrderNo("zc");
        JSONObject jsonObject = new JSONObject(true);
        jsonObject.put("app-name", applicationName);
        jsonObject.put("listener-port", applicationPort);
        jsonObject.put("server-ip", System.getProperty("local-ip") + "----------" + zc);
        jsonObject.put("local-datetime", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        return jsonObject;
    }
}
