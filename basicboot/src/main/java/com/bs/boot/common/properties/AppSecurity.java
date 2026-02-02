package com.bs.boot.common.properties;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
@Getter
@AllArgsConstructor
@ToString
public class AppSecurity {
	private String token;
	private List<String> role;
}
