package com.gunr.bookreviewcolumn.member;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberForm {
	@NotEmpty(message="아이디는 필수항목입니다.")
	@Size(min=4, max=20)
	private String username;
	
	@NotEmpty(message="닉네임은 필수항목입니다.")
	private String nickname;
	
	@NotEmpty(message="비밀번호는 필수항목입니다.")
	private String password;
	
	@NotEmpty(message="비밀번호 확인은 필수항목입니다.")
	private String password2;
	
	@NotEmpty(message="이메일은 필수항목입니다.")
	private String email;
	
	@NotNull(message="나이는 필수항목입니다.")
	private Integer age;

	
}
