package com.bs.boot.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bs.boot.member.service.MemberService;
import com.bs.boot.model.dto.Member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@RequiredArgsConstructor
@RestController
@RequestMapping("/members")
@Slf4j
public class MemberController {
	private final MemberService service;
	@GetMapping("/{id}")
	public Member getMemberById(@PathVariable String id) {
		return service.searchMemberById(id);
	}
	@GetMapping("/all")
	public List<Member> getMemberAll(@RequestParam(defaultValue = "1") int cPage,@RequestParam(defaultValue = "5") int numPerpage) {
		log.info("{}",cPage);
		log.info("{}",numPerpage);
		return service.searchMemberAll();
	}
	
	@GetMapping("/insert")
	public int insertMember(Member m) {
		int a = service.insertMember(m);
		return a;
	}
}
