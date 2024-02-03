package com.zhongni.oauth.controller;

import com.zhongni.oauth.custom.token.CustUsernamePasswordAuthenticationToken;
import com.zhongni.oauth.entity.resp.CommonResponse;
import com.zhongni.oauth.jwt.JwtTokenProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    private JwtTokenProvider jwtTokenProvider;

    /**
     * 简单登录（使用spring security自带逻辑）
     * @param userName
     * @param password
     * @return
     */
     @GetMapping("/sample/login")
     private CommonResponse<String> sampleLogin(@RequestParam String userName, @RequestParam String password){
         //Authenticate the user
         Authentication authenticate = sampleAuthenticate(userName, password);
         //Generate a token with the authentication information
         final String token = jwtTokenProvider.generateToken(authenticate);
         //Return the token to the user
         return CommonResponse.success(token);
     }

    /**
     * 自定义登录（使用自定义的逻辑）
     * @param userName
     * @param password
     * @return
     */
     @GetMapping("/cust/login")
     private CommonResponse<String> custLogin(@RequestParam String userName, @RequestParam String password){
         //Authenticate the user
         Authentication authenticate = custAuthenticate(userName, password);
         //Generate a token with the authentication information
         final String token = jwtTokenProvider.generateToken(authenticate);
         //Return the token to the user
         return CommonResponse.success(token);
     }


    @GetMapping("/query/mine")
    private String aaa1(){
        return "zhangsan";
    }

    private Authentication sampleAuthenticate(String username, String password){
        return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }

    private Authentication custAuthenticate(String username, String password){
        return authenticationManager.authenticate(new CustUsernamePasswordAuthenticationToken(username, password));
    }
}
