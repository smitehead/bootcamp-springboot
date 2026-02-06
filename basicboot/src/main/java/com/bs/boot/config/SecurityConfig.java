package com.bs.boot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsUtils;

import com.bs.boot.rest.model.repository.MemberRepository;
import com.bs.boot.security.AuthenticationDBProvider;
import com.bs.boot.security.MyAccessDenied;
import com.bs.boot.security.MyUnAuthentication;
import com.bs.boot.security.token.JwtFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity 
public class SecurityConfig {

    private final MemberRepository repository;
    private final JwtFilter tokenFilter; 
	
    // 1. DB 인증을 처리할 프로바이더 빈 등록
    @Bean
    AuthenticationDBProvider dbProvider(MemberRepository repository) {
        return new AuthenticationDBProvider(repository, passwordEncoder());
    }
	
    // 2. 비밀번호 암호화 도구 빈 등록
    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 3. 보안 필터 체인 설정 (가장 중요한 문지기 설정)
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http, 
                                    MyAccessDenied accessDined, 
                                    MyUnAuthentication unAuthenticate) throws Exception {
        return http
                // CSRF 보안 비활성화
                .csrf(web -> web.disable())

                // 로그아웃 설정
                .logout(logout -> {
                    logout.logoutUrl("/auth/logout");
                })

                // URL별 접근 권한 설정
                .authorizeHttpRequests(authorize -> {
                    authorize
                    //permitall 여긴 아무나 들어갈수 있음
                    	.requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                        .requestMatchers("*.css", "*.js").permitAll() 
                        .requestMatchers("/auth/**").permitAll()
                        
                        //이러면 demos에 들어갈수 잇는건 admin만 .hasAnyAuthority("admin","...","..." 가능)
                        .requestMatchers("/demos/**").hasAnyAuthority("admin")
                        
                        .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                        .anyRequest().authenticated();               
                })

                // 4. 인증 로직 연결 (DB 대조)
//                .authenticationProvider(dbProvider(repository))
                
                // 5. ★ 추가된 부분: 예외 처리(Exception Handling) 설정
                // 보안 관련 문제가 발생했을 때 우리가 만든 커스텀 핸들러를 사용하도록 등록합니다.
                .exceptionHandling(handle -> {
                    // 권한이 없는 유저가 접근할 때 (403 Forbidden) 실행
                    handle.accessDeniedHandler(accessDined)
                    // 로그인하지 않은 유저가 접근할 때 (401 Unauthorized) 실행
                    .authenticationEntryPoint(unAuthenticate);
                })

                // 6. ★ JWT 필터 위치 설정
                // 모든 요청에서 토큰을 먼저 검사하도록 기본 로그인 필터 앞에 배치
                .addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class)

                .build();
    }
}