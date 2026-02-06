package com.bs.boot.rest.service;

import java.util.List;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bs.boot.rest.model.dto.MemberDTO;
import com.bs.boot.rest.model.entity.MemberEntity;
import com.bs.boot.rest.model.repository.MemberRepository;
import com.bs.boot.security.token.JwtUtils;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service("JpaMemberService")
public class MemberServiceImpl implements MemberService{
	
	private final MemberRepository repository;
	private final JwtUtils jwtUtils;
	private final BCryptPasswordEncoder pwencoder;
	
	@Override
	public MemberDTO searchMemberById(String id) {
		return repository.findById(id).orElseThrow().convert();
	}

	@Override
	public List<MemberDTO> searchMemberAll() {
		// TODO Auto-generated method stub
		return repository.findAll().stream()
				.map(MemberEntity::convert).toList();
	}

	@Transactional
	@Override
	public int insertMember(MemberDTO m) {
		try {
			repository.save(m.convert());
		}catch(RuntimeException e) {
			e.printStackTrace();
			throw new IllegalArgumentException("DB저장실패");
		}
		return 1;
	}
	
	@Transactional
	@Override
	public void updateMember(MemberDTO m) {
		MemberEntity savedM=repository
				.findById(m.getUserId())
				.orElseThrow(()->new RuntimeException());
		savedM.setAge(m.getAge());
		savedM.setAddress(m.getAddress());	
	}
	
	@Transactional
	@Override
	public void deleteMember(String id) {
		repository.deleteById(id);
	}

	@Override
	public String loginToken(MemberDTO m) {
		MemberDTO loginMember=repository.findById(m.getUserId()).orElseThrow(
				()->{throw new BadCredentialsException("인증실패");}).convert();
		
		if(!pwencoder.matches(m.getPassword(),loginMember.getPassword())) {
			throw new BadCredentialsException("인증실패");
		}
			
		return jwtUtils.generateToken(loginMember);
	}
	
	
}
