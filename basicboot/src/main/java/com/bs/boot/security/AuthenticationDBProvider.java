package com.bs.boot.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.bs.boot.rest.model.dto.MemberDTO;
import com.bs.boot.rest.model.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

/**
 * AuthenticationProvider: 인증 전반을 관리하는 인터페이스를 구현함.
 * 실제 DB 데이터를 가져와 사용자가 입력한 정보와 비교하는 클래스입니다.
 */
@RequiredArgsConstructor
public class AuthenticationDBProvider implements AuthenticationProvider {
	
	private final MemberRepository repository; // 사용자 조회를 위한 DB 리포지토리
	private final BCryptPasswordEncoder encoder; // 비밀번호 일치 여부 확인을 위한 암호화 도구
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		// 1. 사용자가 로그인 폼에 입력한 ID와 Password를 가져옴
		String userId = authentication.getName(); // 아이디
		String password = (String) authentication.getCredentials(); // 비밀번호
		
		// 2. DB에서 해당 아이디를 가진 사용자를 조회
		// findById로 찾고, 없으면 BadCredentialsException(인증 실패)을 던짐
		MemberDTO loginMember = repository.findById(userId)
				.orElseThrow(() -> {
					// 보안상 아이디가 없는 건지 비밀번호가 틀린 건지 알려주지 않는 것이 정석임
					throw new BadCredentialsException("아이디, 패스워드가 일치하지 않습니다.");
				}).convert(); // 엔티티를 DTO로 변환
		
		// 3. 비밀번호 검증
		// 사용자가 입력한 비번(password)과 DB의 암호화된 비번(loginMember.getPassword())을 대조
		if (!encoder.matches(password, loginMember.getPassword())) {
			throw new BadCredentialsException("아이디, 패스워드가 일치하지 않습니다.");
		}
		
		// 4. 인증 성공 시 결과물 생성
		// 인증이 완료된 정보를 담은 'Token' 객체를 만들어 반환함
		// (Principal: 로그인한 유저 객체, Credentials: 비번, Authorities: 권한 목록)
		UsernamePasswordAuthenticationToken token =
					new UsernamePasswordAuthenticationToken(
							loginMember, 
							loginMember.getPassword(), 
							loginMember.getAuthorities()
					);
		
		return token; // 리턴된 이 토큰은 시큐리티 세션에 저장되어 로그인 상태를 유지하게 함
	}

	/**
	 * 이 Provider가 어떤 종류의 인증 방식을 처리할 수 있는지 결정함
	 * UsernamePasswordAuthenticationToken 형식의 인증 요청은 이 클래스가 처리하겠다는 뜻
	 */
	@Override
	public boolean supports(Class<?> authentication) {
		return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
	}
}