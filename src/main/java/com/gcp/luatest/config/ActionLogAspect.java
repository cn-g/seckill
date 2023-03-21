package com.gcp.luatest.config;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

/**
 * 接口调用日志
 * 
 * @author Admin
 */
@Aspect
@Component
public class ActionLogAspect {

    private static final Logger log = LoggerFactory.getLogger(ActionLogAspect.class);

    @Pointcut("execution(* com.gcp.luatest.controller.*.*(..))")
    public void controllerMethod() {}

    @Around("controllerMethod()")
    public Object aroundLog(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        RequestAttributes requestAttribute = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes)requestAttribute).getRequest();
        String uri = request.getRequestURI();
        // 打印请求内容
        log.info("开始处理接口{},方法名为{},请求参数为{}", uri, proceedingJoinPoint.getSignature().getName(),
            Arrays.toString(proceedingJoinPoint.getArgs()));
        proceedingJoinPoint.getSignature();
        Object res = proceedingJoinPoint.proceed();
        return res;
    }

}
