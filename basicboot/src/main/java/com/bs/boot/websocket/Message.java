package com.bs.boot.websocket;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data@NoArgsConstructor@AllArgsConstructor@Builder
public class Message {
	private String type;
	private String sender;
	private String receiver;
	private String data;
	private String room;

}
