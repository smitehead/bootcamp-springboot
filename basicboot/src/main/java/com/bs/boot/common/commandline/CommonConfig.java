package com.bs.boot.common.commandline;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.context.WebApplicationContext;

import com.bs.boot.member.service.MemberService;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Order(3)
@Configuration
public class CommonConfig {
	@Bean
	public CommandLineRunner commandLineRunner(WebApplicationContext context, MemberService service) {
		return args->{
			log.info("{}",context.getServletContext().getRealPath("/")); //걍커맨드라인으로 시작시 시작주소가져오기
			service.searchMemberAll().forEach(m->log.info("{}",m));//시작시 서치올 실행
		};
//		return new CommandLineRunner() {
//			
//			@Override
//			public void run(String... args) throws Exception {
//				// TODO Auto-generated method stub
//				
//			}
//		};
	}
}
