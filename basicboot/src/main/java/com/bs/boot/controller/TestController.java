package com.bs.boot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

//@RestController
@Controller
public class TestController {
	
	@GetMapping("/")
	public String index() {
//		return "나의 첫 api서비스";
		return "index";
	}
}
