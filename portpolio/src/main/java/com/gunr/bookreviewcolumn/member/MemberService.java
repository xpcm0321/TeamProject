package com.gunr.bookreviewcolumn.member;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {
	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;
	
	// 회원 조회
	public Member selectUsername(String username) {
	    return memberRepository.findByUsername(username)
	            .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
	}
	public Member selectId(Long id) {
	    return memberRepository.findById(id)
	            .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
	}
	
	public boolean isEmailUsedByActiveMember(String email) {
	    return memberRepository.existsActiveByEmail(email);
	}

	
	public Optional<Member> selectEmail(String email) {
	    return memberRepository.findByEmail(email);
	}
	
	public Member findByEmail(String email) {
	    return memberRepository.findByEmail(email).orElse(null);
	}
	// 회원 비번 확인
	public boolean matchPassword(String rawPassword, String encodedPassword) {
	    return passwordEncoder.matches(rawPassword, encodedPassword);
	}

	
	public Optional<Member> findByOauthId(String oauthId) {
	    return memberRepository.findByOauthId(oauthId);
	}
	
	//insert - 회원가입
	public Member insertMember(Member member) {
		member.setPassword(passwordEncoder.encode(member.getPassword())); // 비번 암호화
		return memberRepository.save(member);
	}
	
	//selectAll
	public List<Member> selectMemberAll(){
		return memberRepository.findAll();
	}

	//select
	public Member selectMember(Long id) {
		return memberRepository.findById(id).get();
	}
	
	//select - email로 username(아이디) 조회
	public String findUsernameByEmail(String email){
		String username = memberRepository.findUsernameByEmail(email);
		return username;
	}

	//update - 패스워드 수정
	// 패스워드 수정 (비밀번호 찾기용)
	@Transactional
	public int updatePasswordByUsernameAndEmail(String username, String email, String encodedPassword) {
	    return memberRepository.updatePasswordByUsernameAndEmail(encodedPassword, username, email);
	}
	
	// 패스워드 수정
	@Transactional
	public int updatePasswordById(Long id, String oldPassword, String newPassword, String newPassword2) {
	    Member member = memberRepository.findById(id)
	                        .orElseThrow(() -> new RuntimeException("회원을 찾을 수 없습니다."));

	    // 현재 비밀번호 일치하는지 확인
	    if (!passwordEncoder.matches(oldPassword, member.getPassword())) {
	        return 0; // 비밀번호 틀림
	    }

	    // 새 비밀번호 암호화
	    String encodedNewPassword = passwordEncoder.encode(newPassword);

	    // 업데이트 수행
	    member.setPassword(encodedNewPassword);
	    //memberRepository.save(member);
	    return 1;
	}	
	
	//update - 닉네임 수정
	@Transactional
	public int updateByNicknameAndId(String nickname, Long id) {
		return memberRepository.updateByNicknameAndId(nickname, id);
	}
	
	/*
	// delete - 계정 삭제
	 public void deleteMember(Long id) {
	        memberRepository.deleteById(id);
	 }
	 
	// delete - 비번 확인후 계정 삭제
	 public void deleteIfPasswordMatches(Member member, String rawPassword) {
		    if (!passwordEncoder.matches(rawPassword, member.getPassword())) {
		        throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
		    }
		    memberRepository.deleteById(member.getId());
	 }
	 */
	 
	 @Transactional
	 public void deactivateMember(Member member) {
		 member.setEnabled(false); // 계정 비활성화
		 member.setOauthId(null);  // 소셜 id 제거
		 member.setAccessToken(null); // 토큰 제거
		 member.setPassword(UUID.randomUUID().toString()); // 비번 제거
		 member.setNickname("탈퇴한 사용자"); // 닉네임 표시
		// 이메일, 아이디 변경 - 재사용 가능하게 
	    member.setEmail("deleted_" + UUID.randomUUID() + "@example.com");
	    member.setUsername("deleted_" + UUID.randomUUID());

	    memberRepository.save(member);
	 }
}

