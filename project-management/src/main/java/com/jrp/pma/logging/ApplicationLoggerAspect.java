package com.jrp.pma.logging;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ApplicationLoggerAspect {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Pointcut("within(com.jrp.pma.controller..*) || within(com.jrp.pma.service..*)")
	public void definePackagePointcuts() {}

	@Before("definePackagePointcuts()")
	public void logBefore(JoinPoint joinPoint) {
//		logger.info("********** BEFORE **********");
//		logger.info(joinPoint.getSignature().getDeclaringTypeName());
//		logger.info(joinPoint.getSignature().getName());
//		logger.info(Arrays.toString(joinPoint.getArgs()));
	}

	@After("definePackagePointcuts()")
	public void logAfter(JoinPoint joinPoint) {
//		logger.info("********** AFTER **********");
//		logger.info(joinPoint.getSignature().getDeclaringTypeName());
//		logger.info(joinPoint.getSignature().getName());
//		logger.info(Arrays.toString(joinPoint.getArgs()));
	}

	@Around("definePackagePointcuts()")
	public Object logAround(ProceedingJoinPoint joinPoint) {
		logger.info("********** BEFORE **********");
		logger.info(joinPoint.getSignature().getDeclaringTypeName());
		logger.info(joinPoint.getSignature().getName());
		logger.info(Arrays.toString(joinPoint.getArgs()));

		Object result = null;
		try {
			result = joinPoint.proceed();
		} catch (Throwable e) {
			logger.equals(e);
		}
		
		logger.info("********** AFTER **********");
		logger.info(joinPoint.getSignature().getDeclaringTypeName());
		logger.info(joinPoint.getSignature().getName());
		logger.info(Arrays.toString(joinPoint.getArgs()));

		return result;
	}
}
