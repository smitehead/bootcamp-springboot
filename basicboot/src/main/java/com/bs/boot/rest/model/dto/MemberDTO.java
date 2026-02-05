package com.bs.boot.rest.model.dto;

import java.sql.Date;

import com.bs.boot.rest.model.entity.MemberEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data@AllArgsConstructor@NoArgsConstructor@Builder
public class MemberDTO {
	private String userId;
	private String password;
	private String userName;
	private Integer age;
	private String gender;
	private String email;
	private String phone;
	private String address;
	private String[] hobby;
	private Date enrollDate;
	
	public MemberEntity convert() {
		return MemberEntity.builder()
				.userId(userId)
				.password(password)
				.userName(userName)
				.age(age)
				.gender(gender)
				.phone(phone)
				.address(address)
				.email(email)
				.hobby(String.join(",",hobby))
				.enrollDate(enrollDate)
				.build();
	}
	
}
