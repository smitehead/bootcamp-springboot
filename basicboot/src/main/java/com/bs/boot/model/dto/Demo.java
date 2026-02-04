package com.bs.boot.model.dto;

import com.bs.boot.model.entity.DemoEntity;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Demo {
	
	
	private Integer devNo;
	@Min(value = 8,message="논란이 좀 있겠는걸")
	private Integer devAge;
	@Email(message = "이메일 형식이 아님")
	private String devEmail;
	private String devGender;
	private String[] devLang;
	@Size(min=2,message = "이름은 2글자 이상")
	private String devName;
	
	
	public DemoEntity convert() {
		String devLang=this.devLang!=null?String.join(",",this.devLang):"";
		return DemoEntity.builder()
				.devNo(devNo)
				.devAge(devAge)
				.devEmail(devEmail)
				.devGender(devGender)
				.devLang(devLang)
				.devName(devName)
				.build();
	}
	
}
