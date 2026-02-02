package com.bs.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.bs.boot.common.properties.AppProperties;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
public class BasicbootApplication {

	public static void main(String[] args) {
		SpringApplication.run(BasicbootApplication.class, args);
	}

}
