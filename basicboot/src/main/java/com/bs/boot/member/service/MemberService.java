package com.bs.boot.member.service;

import java.util.List;

import com.bs.boot.model.dto.Member;
	
public interface MemberService {
	Member searchMemberById(String id);
	List<Member> searchMemberAll();
	int insertMember(Member m);
}
