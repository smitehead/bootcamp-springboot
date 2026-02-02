package com.bs.boot.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PropertiesTestController {
	@Value("${linux.home")
	private String serverPath;
	@Value("${linux.ip}")
	private String serverIp;
	@Value("${linux.port}")
	private String serverPort;
	@Value("${env.path}")
	private String envPath;
	@Value("${env.userkey}")
	private String key;
	
	@Value("${env.javahome}")
	private String javahome;
	
	
	@GetMapping("/property")
	public Map<String,String> myPropertiesData(){
		return Map.of("home",serverPath,"ip",serverIp,"port",serverPort
				,"path",envPath,"javahome",javahome,"key",key);
	}
	
}
