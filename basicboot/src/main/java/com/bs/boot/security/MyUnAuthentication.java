package com.bs.boot.security;

import java.io.IOException;
import java.util.Map;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * AuthenticationEntryPoint: 인증(Authentication) 실패 시 처리를 담당함.
 * 즉, 로그인하지 않은 사용자가 보호된 자원에 접근하려 할 때 실행되는 '첫 관문'입니다.
 */
@Component
public class MyUnAuthentication implements AuthenticationEntryPoint {
	
	// 자바 객체(Map 등)를 JSON 문자열로 바꿔주는 도구
	private ObjectMapper mapper = new ObjectMapper();

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		
		// 1. HTTP 응답 상태 코드를 401(Unauthorized, 인증되지 않음)로 설정
		// 기본적으로 시큐리티는 로그인 안 하면 로그인 페이지로 리다이렉트하지만,
		// REST API 프로젝트에서는 이렇게 401 에러 상태를 직접 주는 것이 정석입니다.
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		
		// 2. 응답 데이터의 형식을 JSON, 인코딩을 UTF-8로 지정
		response.setContentType("application/json;charset=utf-8");
		
		// 3. 클라이언트에게 전달할 에러 메시지 구성
		// 예: {"msg": "Full authentication is required to access this resource"}
		Map<String, String> body = Map.of("msg", authException.getMessage());
		
		// 4. JSON 문자열로 변환하여 응답 바디에 직접 출력
		response.getWriter().print(mapper.writeValueAsString(body));
	}
}