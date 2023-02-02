package com.example.demo.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Configuration
@Aspect
public class LoggingAspect {
    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * Advice: 何を実行するか？ Logging?
     * Pointcut: いつ実行するか？ @Before("execution(* com.example.demo.service.*.*(..))")
     * Aspect: AdviceとPointcutの組み合わせ。
     * Weaver: AOPを実装しているフレームワークのこと。
     */
    @Before("com.example.demo.aop.CommonPointcut.businessPackageConfig()")
    public void before(JoinPoint joinPoint) {
        logger.info("[@Before]" + joinPoint.toString());
    }

    @After("com.example.demo.aop.CommonPointcut.businessPackageConfig()")
    public void after(JoinPoint joinPoint) {
        logger.info("[@After]" + joinPoint.toString());
    }

    /**
     * @AfterReturning はexceptionなしの場合のみ呼び出される。
     */
    @AfterReturning("com.example.demo.aop.CommonPointcut.businessPackageConfig()")
    public void afterReturning(JoinPoint joinPoint) {
        logger.info("[@AfterReturning]" + joinPoint.toString());
    }

    @AfterThrowing(
        pointcut = "com.example.demo.aop.CommonPointcut.businessPackageConfig()",
        throwing = "exception"
    )
    public void afterThrowing(JoinPoint joinPoint, Exception exception) {
        logger.info("[@AfterThrowing]" + joinPoint.toString());
        logger.info("[@AfterThrowing]" + exception.getMessage());
    }
}
