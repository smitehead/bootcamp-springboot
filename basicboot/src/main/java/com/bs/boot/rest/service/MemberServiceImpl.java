package com.bs.boot.rest.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;

import com.bs.boot.rest.model.dto.MemberDTO;
import com.bs.boot.rest.model.entity.MemberEntity;
import com.bs.boot.rest.model.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service("JpaMemberService")
public class MemberServiceImpl implements MemberService{
	
	private final MemberRepository repository;
	
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
		MemberEntity savedM = repository.findById(m.getUserId()).orElseThrow(()->new RuntimeException());
		savedM.setAge(m.getAge());
		savedM.setAddress(m.getAddress());
	}
	@Transactional
	@Override
	public void deleteMember(String id) {
		repository.deleteById(id);
		
	}


	
	
	
	
}
