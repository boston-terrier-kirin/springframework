package com.example.demo.aspect;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Order(2)
@Component
public class LogToCloudAspect {

    @Before("com.example.demo.aspect.CommonPointcut.onlyDao()")
    public void logToCloudBefore() {
        System.out.println("=====> Executing @Before logToCloudBefore");
    }

    @After("com.example.demo.aspect.CommonPointcut.onlyDao()")
    public void logToCloudAfter() {
        System.out.println("=====> Executing @After logToCloudAfter");
    }
}
