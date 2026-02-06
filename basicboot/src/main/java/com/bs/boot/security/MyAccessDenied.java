package com.bs.boot.security;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * AccessDeniedHandler: 인가(Authorization) 실패를 처리하는 클래스
 * 로그인은 되어 있으나, 해당 자원에 접근할 수 있는 '권한(Role)'이 없을 때 실행됩니다.
 */
@Component
public class MyAccessDenied implements AccessDeniedHandler {
    
    @Autowired
    // 자바 객체를 JSON 문자열로 변환하거나 그 반대를 수행하는 도구
    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
            AccessDeniedException accessDeniedException) throws IOException, ServletException {
        
        // 1. 응답 헤더 설정 (우리가 보내는 데이터가 JSON임을 브라우저에 알림)
        response.setContentType("application/json;charset=utf-8");
        
        // 2. 에러 메시지를 담은 Map 생성
        // 예: {"msg": "Access is denied"}
        Map<String, String> body = Map.of("msg", accessDeniedException.getMessage());
        
        // 3. ObjectMapper를 이용해 Map 객체를 JSON 문자열로 변환 후 
        // HTTP 응답 스트림에 직접 출력 (화면 이동이 아닌 데이터 응답)
        response.getWriter().print(mapper.writeValueAsString(body));
    }
}