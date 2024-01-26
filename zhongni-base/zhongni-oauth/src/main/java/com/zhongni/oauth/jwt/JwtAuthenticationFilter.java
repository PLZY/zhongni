package com.zhongni.oauth.jwt;

import com.alibaba.fastjson.JSONObject;
import com.zhongni.oauth.constants.OauthConstants;
import com.zhongni.oauth.enums.BusinessExceptionEnum;
import com.zhongni.oauth.exception.BusinessException;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Resource
    private JwtTokenProvider jwtTokenProvider;

    @Resource
    private UserDetailsService userDetailsService;

    @Value("${jwt.expiration}")
    private Long expirationTime;

    @Value("${exclude.check.url}")
    private String[] excludePaths;

    @Resource
    private AuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String requestURI = request.getRequestURI().replace(OauthConstants.SYSTEM_NAME, "");
        if(ArrayUtils.contains(excludePaths, requestURI) || requestURI.startsWith("/public"))
        {
            filterChain.doFilter(request, response);
            return;
        }

        try
        {
            String jwt = extractJwtFromRequest(request);
            if(!StringUtils.hasText(jwt))
            {
                throw new BusinessException(BusinessExceptionEnum.USER_EXCEPTION_ILLEGAL_LOGIN_INFO);
            }

            Claims tokenInfo = jwtTokenProvider.parseToken(jwt);
            if(tokenInfo.getExpiration().getTime() < new Date().getTime())
            {
                throw new BusinessException(BusinessExceptionEnum.USER_LOGIN_TIME_OUT);
            }

            String subject = tokenInfo.getSubject();
            UserDetails tokenUserDetails = JSONObject.toJavaObject(JSONObject.parseObject(subject), UserDetails.class);
            UserDetails userDetails = userDetailsService.loadUserByUsername(tokenUserDetails.getUsername());
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails,
                    null, userDetails.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        catch (BusinessException e)
        {
            jwtAuthenticationEntryPoint.commence(request, response,
                    new AuthenticationServiceException(e.getMessage(), e));
            return;
        }

        filterChain.doFilter(request, response);
    }

    private String extractJwtFromRequest(HttpServletRequest request)
    {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken)) {
            if(bearerToken.startsWith("Bearer "))
            {
                return bearerToken.substring(7);
            }

            return bearerToken;
        }

        return null;
    }
}