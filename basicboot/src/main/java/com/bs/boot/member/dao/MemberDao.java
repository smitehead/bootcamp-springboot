package com.bs.boot.member.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.bs.boot.model.dto.Member;

public interface MemberDao {
	Member selectMemberById(SqlSession session, String id);
	List<Member> selectMemberAll(SqlSession session);
	int insertMember(SqlSession session, Member m);
}
