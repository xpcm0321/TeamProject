package com.gunr.bookreviewcolumn.member;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class LoginUserAdvice {
	private final MemberService memberService;

    public LoginUserAdvice(MemberService memberService) {
        this.memberService = memberService;
    }

    @ModelAttribute("loginUser")
    public Member loginUser(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) return null;
        return memberService.selectUsername(userDetails.getUsername());
    }
}
