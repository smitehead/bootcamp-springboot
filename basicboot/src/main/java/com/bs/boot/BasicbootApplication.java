package com.bs.boot;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.bs.boot.common.properties.AppProperties;

import lombok.extern.slf4j.Slf4j;
@Slf4j
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
		
		SpringApplication.run(BasicbootApplication.class, args);
	}

}
