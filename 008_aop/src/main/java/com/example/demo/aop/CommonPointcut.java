package com.example.demo.aop;

import org.aspectj.lang.annotation.Pointcut;

public class CommonPointcut {
    @Pointcut("execution(* com.example.demo.service.*.*(..))")
    public void businessPackageConfig() {}

    @Pointcut("bean(*Service*)")
    public void serviceConfig() {}
}
