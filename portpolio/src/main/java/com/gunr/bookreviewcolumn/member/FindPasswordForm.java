package com.gunr.bookreviewcolumn.member;

import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindPasswordForm {
    @NotEmpty(message="새 비밀번호를 입력하세요.")
    private String password;

    @NotEmpty(message="비밀번호 확인을 입력해주세요.")
    private String password2;
}
