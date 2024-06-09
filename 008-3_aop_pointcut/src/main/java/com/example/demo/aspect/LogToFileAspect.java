package com.example.demo.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Aspect
@Order(1)
@Component
public class LogToFileAspect {
    // ■こっちがAdvice
    @Before("com.example.demo.aspect.CommonPointcut.onlyDao()")
    public void logToFileBefore(JoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();

        System.out.println("=====> Executing @Before logToFileBefore -> " + methodSignature);

        String[] params = methodSignature.getParameterNames();
        Object[] paramValues = joinPoint.getArgs();

        for (int i = 0; i < params.length; i ++) {
            System.out.println(params[i] + " -> " + paramValues[i]);
        }

        Stream.of(joinPoint.getArgs())
                .forEach(System.out::println);
    }

    @After("com.example.demo.aspect.CommonPointcut.onlyDao()")
    public void logToFileAfter(JoinPoint joinPoint) {
        System.out.println("=====> Executing @After logToFileAfter -> " + joinPoint.getSignature());
    }
}
