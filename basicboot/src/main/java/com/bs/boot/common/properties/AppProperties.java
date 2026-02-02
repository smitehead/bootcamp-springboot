package com.bs.boot.common.properties;

import java.util.regex.Pattern;

import org.springframework.boot.context.properties.ConfigurationProperties;

//app.bs로 시작하는 프로퍼티스를 찾을 수 있음
@ConfigurationProperties("app.bs")
public class AppProperties {
	
	//키랑 필드명을 일치시켜야 작동함
	private final String name;
	private final String ip;
	private final int port;
	private final AppSecurity security;
	
	
	public AppProperties(String name,String ip,int port,AppSecurity security) {
		System.out.println(name);
		System.out.println(ip);
		System.out.println(port);
	
		this.name=name;
		//정규표현식 사용 이거 ip주소확인 정규표현식임 이렇게 하면 편함
		String reg="^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";
		//패턴 매치를 해서 두개가 다르면
		if(!Pattern.matches(reg, ip)) {
			throw new IllegalArgumentException("ip주소 이상함");
		}
		this.ip=ip;
		if(port<0 || port>65535) {
			throw new IllegalArgumentException("포트번호 초과");
		}
		this.port=port;
		this.security= security;
		System.out.println(this.security);
	}
}
