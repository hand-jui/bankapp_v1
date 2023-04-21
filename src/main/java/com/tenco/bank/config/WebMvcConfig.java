package com.tenco.bank.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.tenco.bank.handler.AuthInterceptor;

//우리가 만든 인터셉트 클래스 등록 + 패턴 설정
@Configuration // IoC 등록 - 2개 이상 빈으로 등록 될 때 사용
public class WebMvcConfig implements WebMvcConfigurer {

	@Autowired // DI
	private AuthInterceptor authInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {

		registry.addInterceptor(authInterceptor).addPathPatterns("/account/**")
												.addPathPatterns("/auth/**"); // 1. path 하나 더 추가하는 법

//		추가 인터셉터 등록 법
//		registry.addInterceptor((new AdminInterceptor());
	}

	@Bean // IoC 관리 대상 처리 - 싱글톤
	public PasswordEncoder passwordEncoder() {

		return new BCryptPasswordEncoder();
	}
}
