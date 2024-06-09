package com.example.demo.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    /**
     * ?は省略可能
     *
     * execution(modifiers-pattern? return-type-pattern declaring-type-pattern? method-name-pattern(param-pattern) throws-pattern?)
     *           *1                 *2                  *3                      *4                  *5              *6
     *
     * public void com.example.demo.dao.AccountDao.addAccount(String) throws Exception)
     * *1     *2   *3                              *4         *5      *6
     *
     * *5 param-pattern
     *   (..) - matches a method with 0 or more params
     *   (*)  - matches a method with one param
     *   ()   - matches a method with no params
     */

    // @Before("execution(public void addAccount())")
    // @Before("execution(public void com.example.demo.dao.AccountDao.addAccount())")
    // @Before("execution(public void add*())")
    // @Before("execution(public * com.example.demo.dao.*.*(..))")
    // @Before("execution(* add*(com.example.demo.model.Account))")
    // @Before("execution(* add*(com.example.demo.model.Account, ..))")
    @Before("execution(* com.example.demo.dao.*.*(..))")
    public void beforeAddAccountAdvice() {
        System.out.println("=====> Executing @Before advice");
    }
}
