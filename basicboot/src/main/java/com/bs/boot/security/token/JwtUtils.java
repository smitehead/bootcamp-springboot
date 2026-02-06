package com.bs.boot.security.token;

import java.time.Duration;
import java.util.Date;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.bs.boot.rest.model.dto.MemberDTO;
import com.bs.boot.rest.model.repository.MemberRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * JwtUtils: JWT 토큰의 생성, 파싱, 검증을 담당하는 유틸리티 클래스
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class JwtUtils {
	
	@Value("${jjwt.issuer}")
	private String issuer; // 토큰 발급자 명칭 (application.properties 등에서 설정)

	// 서버만 알고 있는 비밀 키 (HS512 알고리즘에 적합한 키를 자동으로 빌드)
	private final SecretKey key = Jwts.SIG.HS512.key().build();
	
	private final MemberRepository repository;
	
	/**
	 * 1. 토큰 발급 요청 (기본 유효기간 5일)
	 */
	public String generateToken(MemberDTO m) {
		return createToken(m, Duration.ofDays(5));
	}

	/**
	 * 2. 실제 JWT 토큰 생성 메소드
	 */
	private String createToken(MemberDTO m, Duration exp) {
		// 토큰 만료 시간 계산 (현재 시간 + 설정된 기간)
		Date limit = new Date(new Date().getTime() + exp.toMillis());

		return Jwts.builder()
				// 헤더(Header): 알고리즘 정보와 토큰 타입 지정
				.header().add(Map.of("type", "jwt"))
				.and()
				// 페이로드(Payload) - 등록된 클레임(Registered Claims)
				.subject(m.getUserId())    // 토큰 제목 (주로 사용자 ID)
				.issuer(this.issuer)       // 발급자
				.signWith(key)             // 서버 비밀키로 서명(Signature 생성)
				.expiration(limit)         // 유효 기간 설정
				// 페이로드(Payload) - 공개/비공개 클레임(Custom Claims)
				// 사용자의 PK나 이름 등 추가 정보를 토큰에 담음 (민감 정보 제외)
				.claims(Map.of("id", m.getUserId(), "name", m.getName()))
				.compact(); // 문자열 형태의 토큰으로 압축 반환
	}
	
	/**
	 * 3. 토큰 정보를 읽어 스프링 시큐리티 인증 객체(Authentication)로 변환
	 */
	public Authentication getAuthentication(String token) {
		// 토큰을 해독해서 내부 내용(Claims)을 가져옴
		Claims payload = parseToken(token);
		
		// subject에 담긴 사용자 아이디 추출
		String userId = payload.getSubject();
		
		// DB에서 최신 유저 정보를 다시 조회 (권한 등 최신 상태 확인을 위함)
		MemberDTO loginMember = repository.findById(userId)
				.orElseThrow(() -> {
					throw new BadCredentialsException("인증실패");
				}).convert();

		// 시큐리티 전용 인증 토큰 객체 생성 및 반환
		return new UsernamePasswordAuthenticationToken(
				loginMember, 
				loginMember.getPassword(), 
				loginMember.getAuthorities()
		);
	}
	
	/**
	 * 4. 토큰을 해독(Parsing)하여 Payload(Claims)를 반환하는 내부 메소드
	 */
	private Claims parseToken(String token) {
		return Jwts.parser()
				.verifyWith(key) // 서버 키로 서명이 일치하는지 확인
				.build()
				.parseSignedClaims(token) // 서명된 클레임 해석
				.getPayload(); // 실제 데이터 뭉치(Payload) 반환
	}

	/**
	 * 5. 토큰의 유효성 검증 (만료 여부 및 위변조 확인)
	 */
	public boolean validateToken(String token) {
		try {
			// 파싱 과정에서 만료(Expired)되거나 위변조(Signature)된 경우 예외가 발생함
			Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
			return true; // 예외가 안 나면 정상 토큰
		} catch (Exception e) {
			// 만료된 토큰이거나 가짜 토큰인 경우 로그 출력 후 false 반환
			log.error("유효하지 않은 토큰입니다: {}", e.getMessage());
			return false;
		}
	}
}