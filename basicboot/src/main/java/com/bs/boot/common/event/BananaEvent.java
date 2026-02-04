package com.bs.boot.common.event;

import org.springframework.context.ApplicationEvent;

//커스텀 이벤트를 설정할 때 ApplicationEvent 이벤트를 상속받은 클래스를 선언
public class BananaEvent extends ApplicationEvent{
	private Object source;
	public BananaEvent(Object source) {
		super(source);
	}
	
}
