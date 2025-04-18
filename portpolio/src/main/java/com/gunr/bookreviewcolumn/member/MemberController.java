package com.gunr.bookreviewcolumn.member;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MemberController {
	@Autowired MemberService service;
	
	@GetMapping("/")
	public String main() {
		return "member/login";
		}
	
	@GetMapping("/member/member")
	public String member() {
		return "member/member";
		}
	
	@GetMapping("/member/login")
	public String login() {
		return "member/login";
		}
	

	@GetMapping("/member/join")
	public String join(MemberForm memberForm) {
		return "member/join";
		}

	
	@PostMapping("/member/join")
	public String join(@Valid MemberForm memberForm, BindingResult bindingResult) {
		
		if(bindingResult.hasErrors()) {
			return "member/join";
		}
		if(!memberForm.getPassword().equals(memberForm.getPassword2())) {
			bindingResult.rejectValue("password2", "passwordInCorrect", "패스워드를 확인해주세요.");
			return "member/join";
		}
		try {
			Member member = new Member();
			member.setUsername(memberForm.getUsername());
			member.setNickname(memberForm.getNickname());
			member.setEmail(memberForm.getEmail());
			member.setPassword(memberForm.getPassword());
			service.insertMember(member);
		}catch(DataIntegrityViolationException e) { // 무결성 - 중복키, 외래키제약, 데이터형식불일치
			e.printStackTrace();
			bindingResult.reject("failed", "등록된 유저입니다.");
			return "member/join";
		}catch(Exception e) {
			e.printStackTrace();
			bindingResult.reject("failed", e.getMessage());
			return "member/join";
		}
		
		return "member/login";
	}
	
}
