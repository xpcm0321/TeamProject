package com.gunr.bookreviewcolumn.member;

import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordForm {
	@NotEmpty(message="현재 비밀번호는 필수 정보입니다.")
	private String oldPassword;  
	
	@NotEmpty(message="새 비밀번호는 필수 정보입니다.")
	/*
	@Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}",
	 message="비밀번호는 8~16자 영문 대/소문자, 숫자, 특수문자를 사용해 주세요.")
    */
    private String newPassword; 
	
	@NotEmpty(message="새 비밀번호 확인은 필수 정보입니다.")
    private String newPassword2;
}
