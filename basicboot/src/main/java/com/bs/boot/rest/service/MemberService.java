package com.bs.boot.rest.service;

import java.util.List;

import com.bs.boot.rest.model.dto.MemberDTO;

public interface MemberService {
	MemberDTO searchMemberById(String id);
	List<MemberDTO> searchMemberAll();
	int insertMember(MemberDTO m);
	void updateMember(MemberDTO m);	
	void deleteMember(String id);
}
