package com.gunr.bookreviewcolumn.member;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NicknameForm {
    @NotEmpty(message = "닉네임은 필수 정보입니다.")
    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9-_]{2,10}$",
             message = "닉네임은 특수문자를 제외한 2~10자여야 합니다.")
    private String nickname;
}
