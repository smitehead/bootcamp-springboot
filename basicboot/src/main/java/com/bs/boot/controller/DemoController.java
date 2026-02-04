package com.bs.boot.controller;

import java.util.List;
import java.util.Map;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bs.boot.model.demo.service.DemoService;
import com.bs.boot.model.dto.Demo;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/demos")
public class DemoController {
	
	private final DemoService service;
	
	@GetMapping
	public List<Demo> searchDemoAll(){
		return service.searchDemoAll();
	}
	
	@GetMapping("/{no}")
	public Demo searchDemoByNo(@PathVariable Integer no) {
		return service.searchDemoById(no);
	}
	// /demo?key=?&value=?
	@GetMapping("/demo")
	public List<Demo> searchParamDemo(String key, String value) {
		switch (key) {
		case "devName": return service.findByDevName(value);
		case "devAge" : return service.searchDemoByAgeGreater(Integer.parseInt(value));
		default : return List.of(Demo.builder().build());
		}
	}
	
	@PostMapping
	public List<Map<String,String>> insertDemo(@Valid @RequestBody Demo demo, 
			BindingResult bindResult) {
		
		if(bindResult.hasErrors()) {
			List<Map<String, String>> result=bindResult.getFieldErrors()
					.stream().map(err->Map.of("result","실패","field",err.getField(),
					"message",err.getDefaultMessage())).toList();
			return result;
		}
		
		return List.of(Map.of("result",service.insertDemo(demo)?"성공":"실패"));
		
	}
	
}






