package com.gunr.bookreviewcolumn.review;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gunr.bookreviewcolumn.member.Member;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ReviewController {
	@Autowired ReviewService service;
	
	@GetMapping("/review/detail/{id}")
	public String datail(@PathVariable Long id, Model model) {
		model.addAttribute("dto", service.find(id));
		return "review/detail";
	}
	
	@GetMapping("/review/list")
	public String list(Model model, @RequestParam(value="page", defaultValue="0") int page) {
		model.addAttribute("list", service.getPaging(page));
		System.out.println("..." + this.service.findAll().size());
		model.addAttribute("paging", new PagingDto(this.service.findAll().size(), page));
		return "review/list";
	}
	
	@GetMapping("/review/insert")
	public String review_get() {
		return "redirect:/review/review";
	}
	
	@PostMapping("/review/insert") 
	public String review_post(Review review, Member member) { 
		review.setMember(member); 
		service.insert(review); 
		return "redirect:/review/list"; 
	}
	
	@GetMapping("/review/update/{id}")
	public String update_get(@PathVariable Long id, Model model) {
		model.addAttribute("dto",service.update_view(id));
		return "review/edit";
	}
	
	@PostMapping("/review/update")
	public String update_post(Review review, RedirectAttributes rttr ) {
		String msg="fail";
		if(service.update(review)>0) {msg="글 수정완료!";}
		rttr.addFlashAttribute("msg",msg);
		return "redirect:/review/detail/" +review.getId();
	}
}
