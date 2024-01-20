package com.zhongni.oauth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @GetMapping("/login")
    private String aaa(){
        return "1234";
    }

    @GetMapping("/query/mine")
    private String aaa1(){
        return "zhangsan";
    }
}
