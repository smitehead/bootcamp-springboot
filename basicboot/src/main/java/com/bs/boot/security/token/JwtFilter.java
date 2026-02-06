package com.bs.boot.security.token;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import jakarta.servlet.FilterChain;
import jakarta.servlet.GenericFilter;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

/**
 * JwtFilter: 모든 요청마다 실행되어 사용자의 '토큰 신분증'을 확인하는 클래스
 * GenericFilter를 상속받아 필터로서 동작하며, 요청 헤더에서 토큰을 추출합니다.
 */
@Component
@RequiredArgsConstructor
public class JwtFilter extends GenericFilter {
    
    // 토큰 검증, 정보 추출 등 토큰 관련 도구 모음 클래스
    private final JwtUtils tokenUtils;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        // 1. 요청 객체를 HttpServletRequest로 형변환 (Header 정보를 읽기 위함)
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        
        // 2. 요청 헤더(Header)에서 "Authorization"이라는 이름의 토큰을 꺼내옴
        // 보통 클라이언트가 "Authorization": "Bearer {토큰내용}" 형식으로 보냄
        String token = httpRequest.getHeader("Authorization"); // (주의: 오타 'Autorization' -> 'Authorization' 수정 권장)
        
        // 3. 토큰이 존재하고, 유효한(가짜가 아닌) 토큰인지 검증
        if (token != null && tokenUtils.validateToken(token)) {
            
            // 4. 토큰 내부의 정보를 해석해서 '인증 객체(Authentication)'를 생성
            // (이 안에는 사용자의 ID와 권한 정보 등이 들어있음)
            Authentication auth = tokenUtils.getAuthentication(token);
            
            // 5. ★ 가장 중요한 부분: 생성된 인증 정보를 시큐리티의 '금고(SecurityContext)'에 보관
            // 이렇게 저장해두면, 이후 컨트롤러 등에서 "이 유저 로그인했네?"라고 인식할 수 있음
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        
        // 6. 다음 필터로 요청을 넘김 (이게 없으면 다음 단계로 진행되지 않고 멈춤)
        chain.doFilter(request, response);
    }
}