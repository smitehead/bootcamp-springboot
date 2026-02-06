package com.bs.boot.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.bs.boot.common.interceptor.TestFilter;
import com.bs.boot.common.interceptor.TestInterceptor;

import jakarta.servlet.Filter;

@Configuration
@EnableAspectJAutoProxy
public class WebMvcConfig implements WebMvcConfigurer{
	//정적자원(resources)에 대한 경로설정
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
//		registry.addResourceHandler("/resources/**")
//		.addResourceLocations("/resources/");
//		registry.addResourceHandler("/**")
//		.addResourceLocations("classpath:/templates/");
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(interceptor()).addPathPatterns("/**");
	}
	
	@Bean
	TestInterceptor interceptor() {
		return new TestInterceptor();
	}
	
	//Filter등록, 인터셉터를 등록
	@Bean
	FilterRegistrationBean<Filter> filterRegist(){
		FilterRegistrationBean<Filter> registy=new FilterRegistrationBean<>();
		registy.setFilter(new TestFilter());
		registy.addUrlPatterns("/*");
		registy.setOrder(1);
		return registy;
	}
	//cors허용하기
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		//cors예외는 등록해줌
		//이렇게 하면 모두 허용
		registry.addMapping("/**").allowedOrigins("*");
		//
//		registry.addMapping("/**").allowedOrigins("http://localhost:52330");
		
	}
	

	
}
