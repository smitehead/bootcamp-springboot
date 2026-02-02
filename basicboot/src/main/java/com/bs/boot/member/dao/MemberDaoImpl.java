package com.bs.boot.member.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.bs.boot.model.dto.Member;
@Repository
public class MemberDaoImpl implements MemberDao{

	@Override
	public Member selectMemberById(SqlSession session, String id) {
		// TODO Auto-generated method stub
		return session.selectOne("member.selectMemberById",id);
	}

	@Override
	public List<Member> selectMemberAll(SqlSession session) {
		// TODO Auto-generated method stub
		return session.selectList("member.selectMemberAll");
	}


	@Override
	public int insertMember(SqlSession session, Member m) {
		// TODO Auto-generated method stub
		return session.insert("member.insertMember",m);
	}
	
}
