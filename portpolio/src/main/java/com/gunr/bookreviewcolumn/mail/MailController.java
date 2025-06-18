package com.gunr.bookreviewcolumn.mail;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class MailController {
	@Autowired MailService mailService;
	
	// 찾기 인증메일
	@PostMapping("/sendCode")
	public String sendCode(@RequestParam String to, HttpSession session) {
		if (to == null || to.trim().isEmpty()) {
            return "이메일 주소가 올바르지 않습니다.";
        }

        String code = mailService.sendMail(to);
		if (code == null) {
			return "이메일 전송 실패";
		}
		session.setAttribute("authCode", code);
		session.setAttribute("authEmail", to);
		session.setAttribute("authTime", System.currentTimeMillis()); // 인증번호 시간
		return "인증번호가 발송되었습니다.\n인증번호가 오지 않으면 입력하신 정보가 회원정보와 일치하는지 확인해주세요.";
	}
	
	// 회원가입시 인증메일
	@PostMapping("/sendJoinCode")
	@ResponseBody
	public String sendJoinCode(@RequestParam String email, HttpSession session) {
		 if (email == null || email.trim().isEmpty()) {
	            return "이메일 주소가 올바르지 않습니다.";
	        }

	        String code = mailService.sendMail(email); 
	        if (code == null) {
	            return "이메일 전송 실패";
	        }

	        session.setAttribute("joinAuthCode", code);
	        session.setAttribute("joinAuthEmail", email);
	        session.setAttribute("joinAuthTime", System.currentTimeMillis()); 
	        return "인증번호가 발송되었습니다.";
	}

}
