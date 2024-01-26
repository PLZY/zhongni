package com.zhongni.oauth.controller;

import com.zhongni.oauth.entity.resp.CommonResponse;
import com.zhongni.oauth.jwt.JwtTokenProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private AuthenticationProvider authenticationProvider;

    @Resource
    private JwtTokenProvider jwtTokenProvider;
    @Resource
    private UserDetailsService dBUserDetailsService;


     @GetMapping("/login")
     private CommonResponse<String> aaa(@RequestParam String userName, @RequestParam String password){
         //Authenticate the user
         Authentication authenticate = authenticate(userName, password);
         //Generate a token with the authentication information
         final String token = jwtTokenProvider.generateToken(authenticate);
         //Return the token to the user
         return CommonResponse.success(token);
     }


    @GetMapping("/query/mine")
    private String aaa1(){
        return "zhangsan";
    }

    private Authentication authenticate(String username, String password){
        return authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }
}
