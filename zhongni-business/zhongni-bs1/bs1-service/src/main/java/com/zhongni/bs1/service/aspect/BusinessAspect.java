package com.zhongni.bs1.service.aspect;

import com.zhongni.bs1.common.enums.BusinessExceptionEnum;
import com.zhongni.bs1.common.exception.BusinessException;
import com.zhongni.bs1.common.annotation.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Aspect
@Component
@Slf4j
public class BusinessAspect {

    @Around("execution(public * com.zhongni.bs1.core.service.local..*.*(..))")
    public Object serviceAspect(ProceedingJoinPoint point) throws Throwable {
        long startTime = System.currentTimeMillis();
        Signature signature = point.getSignature();
        String executeMethodAllName = signature.getDeclaringTypeName() + "." + signature.getName();
        log.debug("Service Method: {}, execute start.", executeMethodAllName);
        Object result = point.proceed();
        log.debug("Service Method: {}, execute end. executeTime is : {}ms", executeMethodAllName, System.currentTimeMillis() - startTime);
        return result;
    }

    @Around("execution(public * com.zhongni.bs1.starter.controller..*.*(..))")
    public Object controllerAspect(ProceedingJoinPoint point) throws Throwable {
        MethodSignature signature = (MethodSignature) point.getSignature();
        // 类名+方法名
        String executeMethodAllName = signature.getDeclaringTypeName() + "." + signature.getName();
        // 校验非空属性
        checkNullValueForParams(point.getArgs());
        log.info("Controller Method: {}, execute start...", executeMethodAllName);
        long startTime = System.currentTimeMillis();
        Object result = point.proceed();
        log.info("Controller Method: {}, execute end. executeTime is : {} ms", executeMethodAllName, System.currentTimeMillis() - startTime);
        return result;
    }

    private void getInParamsAllNeedCheckField(Class<?> clazz, List<Field> fieldsList)
    {
        if("java.lang.Object".equals(clazz.getName()))
        {
            return;
        }

        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field field : declaredFields)
        {
            NotNull notnullAnnotation = field.getDeclaredAnnotation(NotNull.class);
            if(null != notnullAnnotation){
                fieldsList.add(field);
            }
        }

        getInParamsAllNeedCheckField(clazz.getSuperclass(), fieldsList);
    }

    private void checkNullValueForParams(Object[] inParams) throws IllegalAccessException
    {
        List<Field> fieldsList = new ArrayList<>();
        for (Object arg : inParams)
        {
            getInParamsAllNeedCheckField(arg.getClass(), fieldsList);
            for (Field field : fieldsList)
            {
                field.setAccessible(true);
                Object value = field.get(arg);
                if(value instanceof String)
                {
                    String v = (String)value;
                    if(StringUtils.isBlank(v))
                    {
                        throw new BusinessException(BusinessExceptionEnum.PARAMS_EXCEPTION_NOT_ALLOW_NULL_VALUE, field.getName());
                    }
                }
                else if(value instanceof Collection)
                {
                    Collection<?> v = (Collection<?>) value;
                    if(CollectionUtils.isEmpty(v))
                    {
                        throw new BusinessException(BusinessExceptionEnum.PARAMS_EXCEPTION_NOT_ALLOW_NULL_VALUE, field.getName());
                    }
                }
                else
                {
                    if(null == value)
                    {
                        throw new BusinessException(BusinessExceptionEnum.PARAMS_EXCEPTION_NOT_ALLOW_NULL_VALUE, field.getName());
                    }
                }
            }
        }
    }
}
