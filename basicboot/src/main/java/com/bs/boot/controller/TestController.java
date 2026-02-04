package com.bs.boot.controller;

import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bs.boot.common.event.BananaEvent;
import com.bs.boot.model.dto.Demo;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

//@RestController
@Controller
@RequiredArgsConstructor
public class TestController {
	private final ApplicationEventPublisher publish;
//	@GetMapping("/")
//	public String index() {
////		return "나의 첫 api서비스";
//		
//		BananaEvent banana =  new BananaEvent("이벤트 생성");
//		publish.publishEvent(banana);
//		return "index";
//	}
	
//	@PostMapping("/demo")
//	@ResponseBody
//	public List<Map<String,String>> insertDemo(@Valid @RequestBody Demo demo, BindingResult bindResult){
//		if(bindResult.hasErrors()) {
//			List<Map<String,String>> responseData = bindResult.getFieldErrors().stream().map(err->Map.of("result","저장실패","field",err.getField(),"message",err.getDefaultMessage())).toList();
//			return responseData;
//		}else {
//			return List.of(Map.of("result","저장성공"));
//		}
//	}
}
