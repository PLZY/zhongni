package com.zhongni.bs1.common.interceptor;

import com.zhongni.bs1.common.constants.CacheKeyConstants;
import com.zhongni.bs1.common.enums.BusinessExceptionEnum;
import com.zhongni.bs1.common.exception.BusinessException;
import com.zhongni.bs1.common.util.CacheUtil;
import eu.bitwalker.useragentutils.UserAgent;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class LoginInterceptor implements HandlerInterceptor {
    /**
     * 在请求处理之前进行调用（Controller方法调用之前）
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
    {
        String token = request.getHeader("token");
        if(StringUtils.isBlank(token)){
            throw new BusinessException(BusinessExceptionEnum.USER_EXCEPTION_ILLEGAL_LOGIN_INFO);
        }

        Object o = CacheUtil.get(CacheKeyConstants.CACHE_TOKEN_PREFIX + token);
        if(null == o) {
            throw new BusinessException(BusinessExceptionEnum.USER_EXCEPTION_ILLEGAL_LOGIN_INFO);
        }

        return true;
    }

    /**
     * 请求处理之后进行调用，但是在视图被渲染之前（Controller方法调用之后）
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        log.info("posthandel执行");
    }

    /**
     * 在整个请求结束之后被调用，也就是在DispatcherServlet 渲染了对应的视图之后执行（主要是用于进行资源清理工作）
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)  {
        log.info("afterCompletion执行");
    }
}
