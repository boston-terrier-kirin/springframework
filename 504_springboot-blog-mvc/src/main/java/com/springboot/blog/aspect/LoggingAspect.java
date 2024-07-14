package com.springboot.blog.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
@Component
public class LoggingAspect {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Pointcut("execution(* com.springboot.blog.controller.*.*(..))")
    private void forController() {}

    @Pointcut("execution(* com.springboot.blog.service.*.*(..))")
    private void forService() {}

    @Pointcut("execution(* com.springboot.blog.repository.*.*(..))")
    private void forRepository() {}

    @Pointcut("forController() || forService() || forRepository()")
    private void forAll() {}

    @Around("forAll()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        String desc = joinPoint.getSignature().toLongString();

        try {
            logger.info("[ST]" + desc);
            Object result = joinPoint.proceed();
            return result;
        } catch (Throwable t) {
            throw t;
        } finally {
            long stop = System.currentTimeMillis();
            logger.info("[EN]" + desc + " " + (stop - start) + "msec");
        }
    }
}
