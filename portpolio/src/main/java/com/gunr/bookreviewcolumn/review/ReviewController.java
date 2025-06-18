package com.gunr.bookreviewcolumn.review;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gunr.bookreviewcolumn.bookdata.Bookdata;
import com.gunr.bookreviewcolumn.bookdata.BookdataRepository;
import com.gunr.bookreviewcolumn.medium.Medium;
import com.gunr.bookreviewcolumn.medium.MediumRepository;
import com.gunr.bookreviewcolumn.member.Member;
import com.gunr.bookreviewcolumn.member.MemberService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ReviewController {
	@Autowired ReviewService service;
	@Autowired MemberService memberService;
	@Autowired LikeService likeService;
	@Autowired MediumRepository mediumRepository;
	@Autowired BookdataRepository bookdataRepository;
	
	@GetMapping("/review")
	public String showReviewPage(@RequestParam(required=false) String mainCategory,
								 @RequestParam(required=false) String subCategory,
								Model model) {
		model.addAttribute("mainCategory", mainCategory);
		model.addAttribute("subCategory", subCategory);
		return "review/review";  // review.html을 반환
	}
	
	/*
	 * @GetMapping("/member/likes/{id}")
	 * 
	 * @ResponseBody public List<Review> getLikedReviews(@PathVariable Long id) {
	 * return likeService.findLikedReviewsByMemberId(id); // 좋아요 한 글 리스트 반환 }
	 */
	
	@PostMapping("/review/like/{id}")
	public String toggleLike(@PathVariable Long id, Principal principal) {
		String username = principal.getName();
		Member member = service.findMemberByUsername(username);
		likeService.toggleLike(member.getId(), id);
		return "redirect:/review/detail/" + id;
	}
	
	@GetMapping("/review/detail/{id}")
	public String datail(@PathVariable Long id, Model model, Principal principal) {
		model.addAttribute("dto", service.find(id));
		model.addAttribute("loginId", principal.getName()); // username 넘김
		return "review/detail";
	}
	
	@GetMapping("/member/mypage/{id}")
	public String myPage(@PathVariable Long id, Model model, Principal principal) {
		model.addAttribute("dto", memberService.selectMember(id));
		model.addAttribute("loginId", principal.getName());
		return "mypage";
	}
	
	@GetMapping("/review/reviewlist")
	public String list(Model model, 
	        @RequestParam(value = "page", defaultValue = "0") int page,
	        @RequestParam(value = "keyword", required = false) String keyword,
	        @RequestParam(value = "tag", required = false) String tag) {

	    if (tag != null && !tag.trim().isEmpty()) {
	        Page<Review> taggedReviews = service.findByMediumName(tag, page);
	        model.addAttribute("list", taggedReviews);
	        model.addAttribute("tag", tag);
	        model.addAttribute("paging", new PagingDto((int) taggedReviews.getTotalElements(), page));
	        return "review/reviewlist";
	    }

	    if (keyword != null && !keyword.trim().isEmpty()) {
	        Page<Review> result = service.searchByTitle(keyword, page);
	        model.addAttribute("list", result);
	        model.addAttribute("paging", new PagingDto(service.searchByTitleCount(keyword), page));
	        model.addAttribute("keyword", keyword);
	        return "review/reviewlist";
	    }

	    Page<Review> result = service.getPaging(page);
	    model.addAttribute("list", result);
	    model.addAttribute("paging", new PagingDto((int) result.getTotalElements(), page));

	    return "review/reviewlist";
	}
	
	@GetMapping("/review/insert")
	public String review_get(Principal principal, Model model) {
		model.addAttribute("nickname", principal.getName());
		return "review/review";
	}
	
	@PostMapping("/review/insert") 
	public String review_post(
		@RequestParam("bookdataId") Long bookdataId,
		@RequestParam("mediumName") String mediumName, 
		Review review, Principal principal
	) { 
		String username = principal.getName();
		Member member = service.findMemberByUsername(username);
		review.setMember(member);
		
		Optional<Medium> mediumOpt = mediumRepository.findByName(mediumName);
		mediumOpt.ifPresent(m -> review.getMedium().add(m)); 
		
		Optional<Bookdata> bookdataOpt = bookdataRepository.findById(bookdataId);
	    bookdataOpt.ifPresent(review::setBookdata);
		
	    service.insert(review); 
		return "redirect:/review/reviewlist"; 
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
	
	@PostMapping("/review/delete/{id}")
	public String delete_post( @PathVariable Long id , RedirectAttributes rttr ){ 
		String msg = "fail";
		if( service.delete(id)  > 0) { msg="글삭제성공!";}
		rttr.addFlashAttribute("msg", msg);
		return "redirect:/review/reviewlist"; 
	} // http://localhost:8080/board/update    ( 글수정 기능 - 갱신된리스트 )
	
	@GetMapping("/member/reviews/{id}")
	@ResponseBody
	public List<ReviewDto> getMyReviews(@PathVariable Long id) {
	    return service.findByMemberId(id).stream()
	        .map(review -> new ReviewDto(
	        		review.getId(), 
	        		review.getReview_title(), 
	        		review.getRating(),
	        		review.getMember().getId(),
	        		review.getReview_date()
	        ))
	        .collect(Collectors.toList());
	}
	@GetMapping("/member/likes/{id}")
	@ResponseBody
	public List<ReviewDto> getLikedReviews(@PathVariable Long id) {
	    return service.findMeLikes(id).stream()
	        .map(review -> new ReviewDto(
	        		review.getId(), 
	        		review.getReview_title(), 
	        		review.getRating(),
	        		review.getMember().getId(),
	        		review.getReview_date()
	        ))
	        .collect(Collectors.toList());
	}
	
	@GetMapping("/member/profile/{id}")
	public String update_profile(@PathVariable Long id, Model model) {
		model.addAttribute("dto",service.update_view(id));
		return "member/profile";
	}
	
	@GetMapping("/review/form")
	public String reviewForm(@RequestParam(required = false) String mediumName, Model model) {
	    List<Medium> selectedMediums = new ArrayList<>();

	    if (mediumName != null && !mediumName.isBlank()) {
	        Optional<Medium> mediumOpt = mediumRepository.findByName(mediumName);
	        mediumOpt.ifPresent(selectedMediums::add);
	    }

	    model.addAttribute("selectedMediums", selectedMediums);
	    model.addAttribute("review", new Review());
	    return "review/review";
	}
}
