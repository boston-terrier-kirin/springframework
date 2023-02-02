package com.example.demo.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Configuration
@Aspect
public class PerformanceAspect {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Around("com.example.demo.aop.CommonPointcut.serviceConfig()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();

        try {
            Object result = joinPoint.proceed();
            return result;
        } catch (Throwable t) {
            throw t;
        } finally {
            long stop = System.currentTimeMillis();
            logger.info("[@Around]" + joinPoint.toString() + " " + (stop - start) + "msec");
        }
    }
}
