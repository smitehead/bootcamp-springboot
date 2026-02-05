package com.bs.boot.rest.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bs.boot.common.properties.AppProperties;
import com.bs.boot.rest.model.dto.MemberDTO;
import com.bs.boot.rest.service.MemberService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController("ApiMemberController")
@RequestMapping("/api/members")
public class MemberController {

    private final AppProperties appProperties;
	
	private final MemberService service;
	
	public MemberController(
			@Qualifier("JpaMemberService") MemberService service, AppProperties appProperties) {
		this.service=service;
		this.appProperties = appProperties;
	}
	
	@GetMapping
	public ResponseEntity<List<MemberDTO>> searchMemberAll(){
		List<MemberDTO> results=service.searchMemberAll();
		if(results.size()>0) {
			//200코드를 전송
			return ResponseEntity.ok().body(results);
		}else {
			return ResponseEntity.noContent().build();
		}
	}
	@GetMapping("/member/{id}")
	public ResponseEntity<MemberDTO> searchMemberById(@PathVariable String id){
		MemberDTO result =  service.searchMemberById(id);
		if(result == null) {
			return ResponseEntity.noContent().build();
			
		}
		else {
			return ResponseEntity.ok(result);
		}
	}
	
	
	@Operation(summary = "회원을저장",description = "회원정보를 json으로 전달받아 저장")
	@ApiResponses(value= {
			@ApiResponse(responseCode = "201",description = "저장 성공"),
			@ApiResponse(responseCode = "422",description = "잘못된 파라미터값")
	})
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
		produces =  MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity saveMember(@Valid @RequestBody MemberDTO m , BindingResult bindResult) {
		if(bindResult.hasErrors()) {
			List<Map<String,String>> paramErrors= bindResult.getFieldErrors().stream().map(err->Map.of("field", err.getField(),"message",err.getDefaultMessage())).toList();
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(paramErrors);
		}
		try {
			service.insertMember(m);
		} catch (IllegalArgumentException e) {
			// TODO: handle exception
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	@PutMapping("/{id}")
	public ResponseEntity update(@PathVariable String id, @RequestBody MemberDTO m) {
		try {
			m.setUserId(id);
			service.updateMember(m);
		} catch (RuntimeException e) {
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.ok(null);

	}
	@DeleteMapping
	@PutMapping("/{id}")
	public ResponseEntity delete(@PathVariable String id) {
		try {
			
			service.deleteMember(id);
		} catch (RuntimeException e) {
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.ok(null);
	}
	// /members/member?key=value & key=value
//	@GetMapping("/member")
//	public ResponseEntity<List<MemberDTO>> searchParam(@RequestParam Map param){
//		
//	}
	
	@PostMapping(value="upload", consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity upload(List<MultipartFile> upfile) {
		System.out.println(upfile);
		return ResponseEntity.ok().build();
	}
}





