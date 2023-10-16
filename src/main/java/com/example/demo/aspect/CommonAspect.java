package com.example.demo.aspect;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Aspect
@Service
public class CommonAspect {
	@AfterReturning("execution(* com.example.demo.controller.*.*(..))")
	public void aspectDemo() {
		System.out.println("ユーザ名：" +  SecurityContextHolder.getContext().getAuthentication().getName());
	}
	
	// TODO 処理成功後にユーザ情報を代入する共通処理、user beanを返したい
	@AfterReturning("execution(* com.example.demo.controller.*.*(..))")
	public void saveUserAspect() {
		
	}
}
