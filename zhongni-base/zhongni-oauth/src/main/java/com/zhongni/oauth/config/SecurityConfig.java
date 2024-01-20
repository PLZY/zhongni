package com.zhongni.oauth.config;

import com.zhongni.oauth.handler.CustomAccessDeniedHandler;
import com.zhongni.oauth.handler.CustomAuthenticationFailureHandler;
import com.zhongni.oauth.service.login.DBUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private DBUserDetailsService dbUserDetailsService;

    @Resource
    private CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

    @Resource
    private CustomAccessDeniedHandler customAccessDeniedHandler;

    /**
     * 配置用户处理逻辑（登录）
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 配置自定义的用户名密码登录逻辑
        auth.userDetailsService(dbUserDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    /**
     * 配置HTTP拦截的URL规则
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/public/**", "/user/login", "/user/register").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .failureHandler(customAuthenticationFailureHandler)
                .permitAll()
                .and()
                .exceptionHandling()
                .accessDeniedHandler(customAccessDeniedHandler)
                .and()
                .logout()
                .permitAll();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // todo 先使用明文存储，后续再优化
        return NoOpPasswordEncoder.getInstance();
    }
}