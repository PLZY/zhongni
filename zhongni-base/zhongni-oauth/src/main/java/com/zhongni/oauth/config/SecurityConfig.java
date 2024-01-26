package com.zhongni.oauth.config;

import com.zhongni.oauth.handler.CustomAccessDeniedHandler;
import com.zhongni.oauth.handler.CustomAuthenticationFailureHandler;
import com.zhongni.oauth.handler.JwtAuthenticationEntryPoint;
import com.zhongni.oauth.jwt.JwtAuthenticationFilter;
import com.zhongni.oauth.service.login.DBUserDetailsService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

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

    @Resource
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Resource
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Value("${exclude.check.url}")
    private String[] excludePaths;

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
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers(excludePaths).permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .formLogin()
                .failureHandler(customAuthenticationFailureHandler) // 认证失败自定义处理器
                .and()
                .exceptionHandling().accessDeniedHandler(customAccessDeniedHandler) // 用户已经通过身份验证，但没有足够的权限来访问请求的资源
                .and()
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint) // 用户未登录却请求需要登录的资源
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 基于jwt令牌，直接配置无状态session
                .and()
                .logout()
                .permitAll()
                ;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // todo 先使用明文存储，后续再优化
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(dbUserDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }
}