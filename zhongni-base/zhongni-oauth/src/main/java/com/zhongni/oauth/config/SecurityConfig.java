package com.zhongni.oauth.config;

import com.zhongni.oauth.security.entrypoint.JwtAuthenticationEntryPoint;
import com.zhongni.oauth.security.handler.CustomAccessDeniedHandler;
import com.zhongni.oauth.security.handler.CustomAuthenticationFailureHandler;
import com.zhongni.oauth.security.handler.Oauth2SuccessHandler;
import com.zhongni.oauth.security.provider.CustAuthenticationProvider;
import com.zhongni.oauth.service.login.CustUserDetailsService;
import com.zhongni.oauth.service.login.DBUserDetailsService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

import javax.annotation.Resource;

@Configuration
@EnableWebSecurity
@EnableAuthorizationServer
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Value("${exclude.check.url}")
    private String[] excludePaths;

    @Resource
    private DBUserDetailsService dbUserDetailsService;

    @Resource
    private CustUserDetailsService custUserDetailsService;

    @Resource
    private CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

    @Resource
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Resource
    private CustomAccessDeniedHandler customAccessDeniedHandler;

    @Resource
    private Oauth2SuccessHandler oauth2SuccessHandler;

    /**
     * 需要手动将AuthenticationManager类暴露在全局中，使我们能在其他功能实现上可以拿到这个认证管理器
     */
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 将一个自定义的Provider和一个spring security自带的Provider都放入Provider集合中，用来模拟多个登录方式下的处理
        auth
                .authenticationProvider(daoAuthenticationProvider())
                .authenticationProvider(custAuthenticationProvider());
    }

    private DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(dbUserDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        //provider.setUserCache();
        return provider;
    }

    private CustAuthenticationProvider custAuthenticationProvider() {
        CustAuthenticationProvider provider = new CustAuthenticationProvider();
        provider.setUserDetailsService(custUserDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        //provider.setUserCache();
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // todo 先使用明文存储，后续再优化
        return NoOpPasswordEncoder.getInstance();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers(excludePaths).permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .failureHandler(customAuthenticationFailureHandler)
//                .successHandler(oauth2SuccessHandler)
//                .and()
//                .exceptionHandling()
//                .authenticationEntryPoint(jwtAuthenticationEntryPoint) // 用户未登录却请求需要登录的资源
//                .accessDeniedHandler(customAccessDeniedHandler) // 用户已经通过身份验证，但没有足够的权限来访问请求的资源
        ;

    }
}