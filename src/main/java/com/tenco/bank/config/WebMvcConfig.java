package com.tenco.bank.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.tenco.bank.handler.AuthInterceptor;
import com.tenco.bank.utils.Define;

//우리가 만든 인터셉트 클래스 등록 + 패턴 설정
@Configuration // IoC 등록 - 2개 이상 빈으로 등록 될 때 사용
public class WebMvcConfig implements WebMvcConfigurer {

	@Autowired // DI
	private AuthInterceptor authInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {

		registry.addInterceptor(authInterceptor).addPathPatterns("/account/**").addPathPatterns("/auth/**"); // 1. path
																												// 하나 더
																												// 추가하는
																												// 법

//		추가 인터셉터 등록 법
//		registry.addInterceptor((new AdminInterceptor());
	}

//	리소스 등록 처리
//	서버 컴퓨터에 위치한 Resource를 활용하는 방법(프로젝트 외부 폴더 접근 방법)
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/images/uploads/**")
				.addResourceLocations("file:///C:\\spring_uplaod\\bank\\upload/");

	}

	@Bean // IoC 관리 대상 처리 - 싱글톤
	public PasswordEncoder passwordEncoder() {

		return new BCryptPasswordEncoder();
	}
}
