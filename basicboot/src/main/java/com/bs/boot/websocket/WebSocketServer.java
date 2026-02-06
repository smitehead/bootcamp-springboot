package com.bs.boot.websocket;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class WebSocketServer extends TextWebSocketHandler{
	
	private Map<String,WebSocketSession> clients=new HashMap();
	private ObjectMapper mapper=new ObjectMapper();
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		//클라이언트가 접속 요청했을때
		//js -> new WebScoket()를 호출했을때
		// WebSocketSession객체를 접속한 사용자의 접속정보를 제공
		log.debug("클라이언트 접속");
//		clients.add(session);
	}

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		//접속한 클라이언트가 데이터를 전송하면 실행되는 메소드
		//js -> send('데이터')메소드를 호출하면 실행
		log.debug("전송된 데이터 : "+message.getPayload());
		
		Message msg=mapper.readValue(message.getPayload(), Message.class);
		
		switch(msg.getType()) {
			case "open" : addClient(session,msg);break;
			case "msg" : sendMessage(msg);break;
		}
		
//		clients.stream().filter(client->client.isOpen())
//		.forEach(client->{
//			try {
//				client.sendMessage(new TextMessage(message.getPayload()));
//			}catch(IOException e) {
//				e.printStackTrace();
//			}
//		});
//		session.sendMessage(new TextMessage(message.getPayload()+"님 환영합니다"));
	
	}
	private void addClient(WebSocketSession session,Message msg) {
		clients.put(msg.getSender(),session);
		sendMessage(msg);
		attendMessage();//참여자 정보를 전송
		//deleteClient();
	}
	private void attendMessage() {
		try {
			Set<String> clientId=clients.keySet();
			Message attendClient=Message.builder()
					.type("attend")
					.data(mapper.writeValueAsString(clientId))
					.build();
			sendMessage(attendClient);
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	private void sendMessage(Message msg) {
		try {
			if(msg.getReceiver()!=null && !msg.getReceiver().equals("")) {
				clients.get(msg.getReceiver())
				.sendMessage(new TextMessage(mapper.writeValueAsString(msg)));
			}else {
				clients.forEach((userId,session)->{
					try {
						if(session.isOpen())
							session.sendMessage(new TextMessage(mapper.writeValueAsString(msg)));
					}catch(IOException e) {
						e.printStackTrace();
					}
				});
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		//클라이언트가 접속 종료했을때
		//deleteClent();
	
	}

	
	
}
