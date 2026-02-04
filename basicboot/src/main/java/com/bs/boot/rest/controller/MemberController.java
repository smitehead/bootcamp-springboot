package com.bs.boot.rest.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bs.boot.rest.model.dto.MemberDTO;

@RestController
@RequestMapping("/members")
public class MemberController {
	
	@GetMapping
	//ResponseEntity를 이젠 사용해서 응답시 좀 편하게 응답할 수 있ㄷ록 변경
	public ResponseEntity<List<MemberDTO>> searchMemberAll(){
		List<MemberDTO> result = service.searchMemberAll();
		if(result.size() > 0) {
			return ResponseEntity.ok().body(result);
		}else { 
			return ResponseEntity.noContent().build();
		}
	}
}
