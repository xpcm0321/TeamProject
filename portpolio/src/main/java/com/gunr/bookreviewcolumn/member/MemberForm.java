package com.gunr.bookreviewcolumn.member;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberForm {
	@NotEmpty(message="아이디는 필수 정보입니다.")
	@Pattern(regexp = "^[a-z0-9]{4,20}", 
			 message="아이디는 4~20자 영문 소문자와 숫자만 사용 가능합니다.")
	private String username;
	
	@NotEmpty(message="닉네임은 필수 정보입니다.")
	@Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9-_]{2,10}$", 
	 			message="닉네임은 특수문자를 제외한 2~10자여야 합니다.")
	private String nickname;
	
	@NotEmpty(message="비밀번호는 필수 정보입니다.")
	@Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}",
 			 message="비밀번호는 8~16자 영문 대/소문자, 숫자, 특수문자를 사용해 주세요.")

	private String password;
	
	@NotEmpty(message="비밀번호 확인을 입력해주세요.")
	private String password2;
	
	
	@NotEmpty(message="이메일은 필수 정보입니다.")
	private String email;
	
	//private Integer age;

	private String provider;
	
	private String oauthId;

	private Long id;
	
    private String accessToken;
}
