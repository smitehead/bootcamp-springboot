package com.bs.boot;

import java.util.Properties;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.annotation.Order;

import com.bs.boot.common.properties.AppProperties;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Order(2)
@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
public class BasicbootApplication implements CommandLineRunner {

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		log.info("{}",args);
		log.info("CommandLineRunner실행");
		
	}

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(BasicbootApplication.class);
		Properties envProp = new Properties();
		envProp.setProperty("server.port", "9999");
		app.setDefaultProperties(envProp);
		
		/*	프로퍼티 실행순서
		 *	1. setDefaultProperties()로 설정한값 
		 *	2. @PropertySource로 설정한 내용
		 *	3.application.properties/application.yml 설정내용
		 * 	4.명령형 인자로 설정한값 ->
		 */
		
		app.run(args);
//		SpringApplication.run(BasicbootApplication.class, args);
	}

}
