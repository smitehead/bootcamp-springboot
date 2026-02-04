package com.bs.boot.member.service;

import java.util.List;
import java.util.concurrent.Flow.Publisher;

import org.apache.ibatis.session.SqlSession;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import com.bs.boot.common.event.BananaEvent;
import com.bs.boot.member.dao.MemberDao;
import com.bs.boot.model.dto.Member;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor

public class MemberServiceImpl implements MemberService {
	private final SqlSession session;
	private final MemberDao dao;
	private final ApplicationEventPublisher publisher;
	
	
	@Override
	public Member searchMemberById(String id) {
		// TODO Auto-generated method stub
		return dao.selectMemberById(session, id);
	}
	@Override
	public List<Member> searchMemberAll() {
		// TODO Auto-generated method stub
		return dao.selectMemberAll(session);
	}
	
	@Override
	public int insertMember(Member m) {
		publisher.publishEvent(new BananaEvent(m));
		return dao.insertMember(session ,m);
	}


}
