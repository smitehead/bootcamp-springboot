package com.bs.boot.common.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
@Component
@Aspect
@Slf4j
public class MyAspect {
	@Before("execution(*com.bs.boot.controller..*(..))") //이거 조인포인트 (언제 실행할건지)
	public void beforeAdvice(JoinPoint joinpoint) {
		log.debug("====aop적용====");
	}
}
