package com.bs.boot.common.event;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class SpringBootEventHandler {
	
	@EventListener(ApplicationReadyEvent.class)//실행시점 : 완전히 끝난후
	public void applicationRaeadyEvent(ApplicationReadyEvent event) throws Exception{
		log.info("applicationReadyEvent발생!! ");
		log.info("{}",event.getSpringApplication());
		log.info("{}",event.getApplicationContext());
	}
	@EventListener(WebServerInitializedEvent.class)//실행시점 : 서버가 시작된 직후
	public void webServerStart(WebServerInitializedEvent event) throws Exception{
		log.info("서버실행 완료!!");
		log.info("{}",event.getWebServer().getPort());
	}
	@EventListener(BananaEvent.class)
	public void testEvent(BananaEvent event) {
		log.info("banana! na ba!");
		log.info("{}",event.getSource());
	}
}
