package com.bs.boot.common.commandline;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Order(1)
@Component
@RequiredArgsConstructor
@Slf4j
public class StartSetting implements CommandLineRunner{
	private final Environment env;
	private final WebApplicationContext webContext;
	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		log.info("activeProfile={)",env.getProperty("spring.profiles.active"));
		log.info("database{}",env.getProperty("spring.datasource.url"));
		long count = Arrays.stream(webContext.getBeanDefinitionNames()).filter(bean->bean.contains("bean")).count();
		log.info("{}",count);
	}
	

	
}
