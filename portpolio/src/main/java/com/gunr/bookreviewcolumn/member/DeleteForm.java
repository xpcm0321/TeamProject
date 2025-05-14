package com.gunr.bookreviewcolumn.member;

import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeleteForm {
    @NotEmpty(message = "비밀번호는 필수입니다.")
    private String password;
}