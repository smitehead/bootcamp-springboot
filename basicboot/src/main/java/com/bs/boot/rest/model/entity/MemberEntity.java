package com.bs.boot.rest.model.entity;

import java.sql.Date;

import com.bs.boot.rest.model.dto.MemberDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data@AllArgsConstructor@NoArgsConstructor@Builder

@Entity
@Table(name="MEMBER")

public class MemberEntity {
	@Id
	private String userId;
	private String password;
	private String userName;
	private Integer age;
	private String gender;
	private String email;
	private String phone;
	private String address;
	private String hobby;
	private Date enrollDate;
	
	public MemberDTO convert() {
		return MemberDTO.builder()
				.userId(userId)
				.password(password)
				.userName(userName)
				.age(age)
				.gender(gender)
				.phone(phone)
				.address(address)
				.email(email)
				.hobby(hobby.split(","))
				.enrollDate(enrollDate)
				.build();
	}
}
