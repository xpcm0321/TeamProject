package com.gunr.bookreviewcolumn.image;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gunr.bookreviewcolumn.member.Member;
import com.gunr.bookreviewcolumn.member.MemberRepository;
import com.gunr.bookreviewcolumn.member.MemberService;

@Controller
public class ImageController {
	
	@Autowired ImageRepository imageRepository;
	@Autowired MemberService memberService;
	@Autowired MemberRepository memberRepository;
	
	// 사용자에게 이미지 보여주기
	@GetMapping("/member/selectProfile")
	public String showImageOptions(Model model) {
	    List<Image> imageList = imageRepository.findAll();
	    model.addAttribute("images", imageList);
	    return "member/profile"; // profile.html
	}
	


}
