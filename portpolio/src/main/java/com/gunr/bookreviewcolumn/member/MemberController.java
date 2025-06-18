package com.gunr.bookreviewcolumn.member;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gunr.bookreviewcolumn.image.Image;
import com.gunr.bookreviewcolumn.image.ImageRepository;
import com.gunr.bookreviewcolumn.oauth2.SocialUnlinkService;

@Controller
public class MemberController {
	@Autowired MemberService service;
	@Autowired MemberRepository memberRepository;
	@Autowired private SocialUnlinkService socialUnlinkService;
	@Autowired ImageRepository imageRepository;
	
	private final PasswordEncoder passwordEncoder;

    public MemberController(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
	
	// 사이트 처음 들어갈때
	@GetMapping("/")
	public String main(Model model) {
		return "member/login";
	}
	
	// 로그인 폼
	@GetMapping("/member/login")
	public String login(Model model) {
		return "member/login";
	}

	
	// 회원가입 폼
	@GetMapping("/member/join")
	public String join(MemberForm memberForm, Model model) {
		return "member/join";
		}
	
	// 회원가입 기능
	@PostMapping("/member/join")
	public String join(@Valid MemberForm memberForm, 
								BindingResult bindingResult, 
								HttpSession session, 
								HttpServletRequest request) {
			
		Boolean joinEmailVerified = (Boolean) session.getAttribute("joinEmailVerified");
	    if (joinEmailVerified == null || !joinEmailVerified) {
	        bindingResult.reject("emailNotVerified", "이메일 인증을 완료해야 회원가입이 가능합니다.");
	        return "member/join";
	    }

		if(!memberForm.getPassword().equals(memberForm.getPassword2())) {
			//bindingResult.rejectValue(필드명, 오류코드, 에러메시지)
			bindingResult.rejectValue("password2", "passwordInCorrect", "비밀번호를 확인해주세요.");
			
			return "member/join";
		}
		if (service.isEmailUsedByActiveMember(memberForm.getEmail())) {
		    bindingResult.rejectValue("email", "duplicate", "이미 가입된 이메일입니다.");
		    return "member/join";
		}

		try {
			Member member = new Member();
			Image defaultImage = imageRepository.findByImg("/images/profile1.png");
			if (defaultImage == null) {
			    throw new RuntimeException("기본 이미지가 없습니다.");
			}

			member.setImage(defaultImage);
			member.setMemberimg(defaultImage.getImg());

			member.setUsername(memberForm.getUsername());
			member.setNickname(memberForm.getNickname());
			member.setEmail(memberForm.getEmail());
			member.setPassword(memberForm.getPassword());
			member.setAge(0);  // 나이 기본값 0
			
			service.insertMember(member);
			
			User userDetails = new User(
					member.getUsername(),
					member.getPassword(),
					List.of(new SimpleGrantedAuthority("ROLE_MEMBER"))
					);
			
			forceLogin(userDetails, request);  // 로그인 세션 유지
			
		}catch(DataIntegrityViolationException e) { // 무결성 - 중복키, 외래키제약, 데이터형식불일치
			e.printStackTrace();
			bindingResult.reject("failed", "등록된 유저입니다.");
			
			return "member/join";
		}catch(Exception e) {
			e.printStackTrace();
			bindingResult.reject("failed", e.getMessage());
			
			return "member/join";
		}
		
	   
	        return "redirect:/board/main";
		}
		
	// 회원가입시 이메일 인증코드
	@PostMapping("/verifyJoinCode")
	@ResponseBody
	public String verifyJoinCode(@RequestParam String joinCode, HttpSession session) {
	    String authCode = (String) session.getAttribute("joinAuthCode");
	    Long authTime = (Long) session.getAttribute("joinAuthTime");

	    if (authCode == null || authTime == null) {
	        return "인증코드가 없습니다.";
	    }

	    long now = System.currentTimeMillis();
	    long timeLimit = 3 * 60 * 1000; // 3분

	    if (now - authTime > timeLimit) {
	        session.removeAttribute("joinAuthCode");
	        session.removeAttribute("joinAuthTime");
	        return "인증시간 만료";
	    } else if (!authCode.equals(joinCode.trim())) {
	        return "인증실패";
	    } else {
	        session.setAttribute("joinEmailVerified", true); 
	        return "인증성공";
	    }
	}

	// admin이라면 adminpage로 이동
	@GetMapping("/admin/adminpage")
	public String aminpage(MemberForm memberForm) {
		return "admin/adminpage";
	}
	
	// member이라면 mypage로 이동
	@GetMapping("/member/mypage")
	public String mypage(Model model, Authentication auth) {
		 String username = auth.getName();
		 Member member = service.selectUsername(username);
		 
		 model.addAttribute("member", member);
		 model.addAttribute("dto", member);
		 
		 model.addAttribute("member", member);
		return "/member/mypage";
	}
	
	// 회원정보
	@GetMapping("/member/memberInfo")
	public String memberInfo(Model model, Authentication authentication) {
		if (authentication == null || !authentication.isAuthenticated()) {
			return "redirect:/member/login"; // 인증 안됐으면 로그인으로
		}

		String username = authentication.getName(); // 로그인된 username 꺼내기
		Member member = service.selectUsername(username); // username으로 DB에서 조회

		model.addAttribute("dto", member); // 조회한 member를 모델에 넣기
		return "member/memberInfo";
		}
	
	@PostMapping("/member/setProfile")
	public String setProfile(@RequestParam("imageId") Long imageId,
	                         @RequestParam("memberimg") String memberimg,
	                         Principal principal) {

	    String username = principal.getName();
	    Member member = memberRepository.findByUsername(username)
	        .orElseThrow(() -> new RuntimeException("회원 없음"));

	    Image image = imageRepository.findById(imageId)
	        .orElseThrow(() -> new RuntimeException("이미지 없음"));

	    member.setImage(image);            
	    member.setMemberimg(memberimg);    

	    memberRepository.save(member);
	    return "redirect:/member/mypage";
	}



///////////////////////////
	
	// 아이디 중복 체크
	@GetMapping(value = "/idByUsername/{username}", produces = "application/json; charset=UTF-8")
	@ResponseBody
	public Map<String, Object> idByUsername(@PathVariable String username){
		Map<String, Object> result = new HashMap<>();
		String msg = "사용불가능합니다.";
		try {
			service.selectUsername(username);
		}catch(Exception e) {msg = "사용가능합니다.";}
		
		result.put("result", msg);
		return result;
	}

	
///////////////////////// 
	
	// 아이디 찾기
	@GetMapping("/member/find_id")
	public String findId(MemberForm memberForm) {
		return "member/find_id";
	}
	
	// 아이디 찾기 인증
	@PostMapping("/verifyCode")
	@ResponseBody
	public String verifyCodeAndGetUsername(@RequestParam String userNum, HttpSession session) {
		String authCode = (String) session.getAttribute("authCode");
		Long authTime = (Long) session.getAttribute("authTime");
		String email = (String) session.getAttribute("authEmail");
		String username = service.findUsernameByEmail(email);
		
		if(authCode == null || authTime == null || email == null) {
			return "인증코드가 없습니다.";
		}
		
		// 인증 유효 시간
		long now = System.currentTimeMillis();
		long timeLimit = 3 * 60 * 1000;
		
		if(now - authTime > timeLimit) {
			session.removeAttribute("authCode");
			session.removeAttribute("authTime");
			return "인증시간 만료";
		} else if(!authCode.equals(userNum.trim())) {  //trim() : 공백 제거
		    return "인증실패";
		} else if(username == null) {
	        return "회원정보 없음";
	    } else {
	        session.setAttribute("findPassUsername", username);
	        session.setAttribute("findPassEmail", email);
	        
	    	return "인증성공";}
	}
	
	// 아이디 찾기 폼
	@GetMapping("/member/find_id_result")
	public String findIdResultPage(Model model, HttpSession session) {
	    String email = (String) session.getAttribute("authEmail");
	    if (email == null) {
	        return "redirect:/member/find_id";
	    }

	    Member member = service.findByEmail(email); 

	    if (member != null) {
	        model.addAttribute("username", member.getUsername());
	        model.addAttribute("joinDate", member.getDatetime()); 
	    } else {
	        model.addAttribute("username", null); 
	    }

	    return "member/find_id_result";
	}

			
	// 비밀번호 찾기
	@GetMapping("/member/find_pass")
	public String findPass(MemberForm memberForm) {
		return "member/find_pass";
	}

	// 비밀번호 찾기 인증
	@PostMapping("/verifyCodeForFindPass")
	@ResponseBody
	public String verifyCodeForFindPass(@RequestParam String username,
		                                @RequestParam String email,
		                                @RequestParam String userNum,
		                                HttpSession session, Model model) {

		String authCode = (String) session.getAttribute("authCode");
		Long authTime = (Long) session.getAttribute("authTime");

		// 인증 유효시간 체크 (3분)
		long now = System.currentTimeMillis();
		long timeLimit = 3 * 60 * 1000;

			if (authCode == null || authTime == null || now - authTime > timeLimit) {
				session.removeAttribute("authCode");
				session.removeAttribute("authTime");
				model.addAttribute("error", "인증시간 만료");
				
				return "member/find_pass";
			}

			if (!authCode.equals(userNum.trim())) {
		            model.addAttribute("error", "인증 실패");
		            return "member/find_pass";
		    }

			// 인증 성공
			session.setAttribute("findPassUsername", username);
			session.setAttribute("findPassEmail", email);

		        return "인증성공";
		    }

	// 비밀번호 재설정 폼 
	@GetMapping("/member/find_pass_result")
	public String findPassResultPage(Model model) {
		model.addAttribute("findPasswordForm", new FindPasswordForm());
			        return "member/find_pass_result";
	}

	// 비밀번호 변경 처리
	@PostMapping("/member/find_pass_result")
	public String updatePassword(@Valid @ModelAttribute("findPasswordForm") FindPasswordForm findPasswordForm,
	                             BindingResult bindingResult,
	                             HttpSession session,
	                             Model model) {

	    String username = (String) session.getAttribute("findPassUsername");
	    String email = (String) session.getAttribute("findPassEmail");

	    if (username == null || email == null) {
	        return "redirect:/member/find_pass";
	    }

	    if (bindingResult.hasErrors()) {
	        return "member/find_pass_result";
	    }

	    if (!findPasswordForm.getPassword().equals(findPasswordForm.getPassword2())) {
	        bindingResult.rejectValue("password2", "passwordMismatch", "비밀번호가 일치하지 않습니다.");
	        return "member/find_pass_result";
	    }

	    String encodedPassword = passwordEncoder.encode(findPasswordForm.getPassword());
	    int result = service.updatePasswordByUsernameAndEmail(username, email, encodedPassword);

	    if (result == 0) {
	        model.addAttribute("error", "비밀번호 변경 실패");
	        return "member/find_pass_result";
	    }

	    session.removeAttribute("findPassUsername");
	    session.removeAttribute("findPassEmail");

	    return "redirect:/member/login?resetSuccess";
	}


	// 닉네임 변경 폼
	@GetMapping("/member/updateNickname")
	public String updateNicknameForm(Model model, Authentication authentication) {
		String username = authentication.getName();
		Member member = service.selectUsername(username);

		NicknameForm nicknameForm = new NicknameForm();
		nicknameForm.setNickname(member.getNickname());
		model.addAttribute("nicknameForm", nicknameForm);
		return "/member/updateNickname";
		}

	// 닉네임 변경 기능
	@PostMapping("/member/updateNickname")
	public String updateNickname(@Valid @ModelAttribute NicknameForm nicknameForm, 
												 BindingResult bindingResult,
												 Authentication authentication, 
												 Model model) {
		if (bindingResult.hasErrors()) {
	        return "member/updateNickname";  // 유효성 
	    }
		
		if (authentication == null || !authentication.isAuthenticated()) {
			return "redirect:/member/login";
		}

		String username = authentication.getName(); // 로그인된 username 꺼내기
		Member member = service.selectUsername(username); // username으로 회원 조회
		Long memberId = member.getId(); // memberId 

		String newNickname = nicknameForm.getNickname();
		System.out.println("newNickname = " + newNickname);

		if (newNickname == null || newNickname.trim().isEmpty()) {
			model.addAttribute("error", "닉네임을 입력해주세요.");
			return "member/updateNickname";
		}

		int result = service.updateByNicknameAndId(newNickname, memberId);
		System.out.println("update result = " + result);

		if (result > 0) {
			return "redirect:/member/memberInfo"; // 성공
		} else {
			model.addAttribute("error", "닉네임 변경 실패. 다시 시도해주세요.");
			return "member/updateNickname";
		}
		}
	
	// 비밀번호 변경(회원정보-수정)
	@GetMapping("/member/updatePassword")
	public String updatePasswordForm(Model model) {
		model.addAttribute("passwordForm", new PasswordForm());
		return "/member/updatePassword";
		}
	
	@PostMapping("/member/updatePassword")
	public String updatePassword(@Valid @ModelAttribute PasswordForm passwordForm, 
	                             BindingResult bindingResult,
	                             Authentication authentication, 
	                             Model model) {
		if (bindingResult.hasErrors()) {
	        return "member/updatePassword";  // 유효성 
	    }

	    // 새 비밀번호, 새 비밀번호 확인 일치 여부 검사
	    if (!passwordForm.getNewPassword().equals(passwordForm.getNewPassword2())) {
	        bindingResult.rejectValue("newPassword2", "passwordMismatch", "새 비밀번호가 일치하지 않습니다.");
	        return "member/updatePassword";
	    }

	    try {
	        // 로그인한 사용자의 username -> member 조회 -> id 확보
	        String username = authentication.getName();
	        Member member = service.selectUsername(username);
	        model.addAttribute("member", member);
	        Long memberId = member.getId();

	        int result = service.updatePasswordById( memberId, passwordForm.getOldPassword(), passwordForm.getNewPassword(), passwordForm.getNewPassword2() );

	        if (result > 0) {
	            return "redirect:/member/memberInfo";
	        } else {
	            model.addAttribute("error", "현재 비밀번호가 일치하지 않습니다.");
	            return "member/updatePassword";
	        }

	    } catch (Exception e) {
	        model.addAttribute("error", "비밀번호 변경에 실패했습니다: " + e.getMessage());
	        return "member/updatePassword";
	    }
	}


	// 회원탈퇴(계정삭제) 폼
	@GetMapping("/member/deleteMember")
	public String deleteMemberForm(Model model, Authentication authentication) {
	    String loginId = authentication.getName();
	    Member member = service.selectUsername(loginId);
	    model.addAttribute("member", member); 
	    model.addAttribute("deleteForm", new DeleteForm());
	    
	    return "member/deleteMember";
	}
	
	// 회원탈퇴 기능
	@PostMapping("/member/deleteMember")
	public String deleteMember(@Valid @ModelAttribute DeleteForm deleteForm,
	                           BindingResult bindingResult,
	                           Model model,
	                           Authentication authentication) {

	    // 사용자 정보 
	    Member member = service.selectUsername(authentication.getName());
	    model.addAttribute("member", member);
	    
	    // 일반 로그인 회원 - 비밀번호 검증
	    if (member.getProvider() == null) {
	        if (bindingResult.hasErrors()) {
	        	model.addAttribute("deleteForm", deleteForm);
	            return "member/deleteMember";
	        }

	        // 비밀번호 불일치
	        if (!passwordEncoder.matches(deleteForm.getPassword(), member.getPassword())) {
	            bindingResult.rejectValue("password", "invalid", "비밀번호가 일치하지 않습니다.");
	            return "member/deleteMember";
	        }
	    }

	    // 소셜 로그인 회원 - 연동 해제
	    if (member.getProvider() != null && member.getAccessToken() != null) {
	        socialUnlinkService.unlink(member.getProvider(), member.getAccessToken());
	    }

	    // 계정 비활성화
	    service.deactivateMember(member);

	    // 로그아웃
	    SecurityContextHolder.clearContext();

	    return "redirect:/";
	}
	
	// 로그인 세션 저장
	private void forceLogin(User userDetails, HttpServletRequest request) {
	    UsernamePasswordAuthenticationToken auth =
	        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

	    SecurityContext context = SecurityContextHolder.createEmptyContext();
	    context.setAuthentication(auth);
	    SecurityContextHolder.setContext(context);

	    HttpSession session = request.getSession(true);
	    session.setAttribute("SPRING_SECURITY_CONTEXT", context);
	}

	
}
