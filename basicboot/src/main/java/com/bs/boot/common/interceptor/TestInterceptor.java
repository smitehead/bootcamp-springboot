package com.bs.boot.common.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
@Slf4j
public class TestInterceptor implements HandlerInterceptor {
	@Override
		public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
				throws Exception {
			// TODO Auto-generated method stub
		log.debug("인터셉터 사용");
			return true;
		}
}
