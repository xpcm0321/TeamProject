package com.gunr.bookreviewcolumn.member;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {
	private final MemberRepository memberRepository;
	//private final PasswordEncoder passwordEncoder;
	
	//insert - 회원가입
	public Member insertMember(Member member) {
		return memberRepository.save(member);
	}
	
	//selectAll
	public List<Member> selectMemberAll(){
		return memberRepository.findAll();
	}

	//select - 유저 한명 조회(유저네임)
	public Member selectUsername(String usernaem) {
		return memberRepository.findByUsername(usernaem).get();
	}
	
	
	//update - 패스워드 수정
	public int updateByPassword(Member member, String old, String username) {
		return memberRepository.updateByPasswordAndUsername(
				member.getPassword(), old, member.getUsername());
	}
	//update - 닉네임 수정
	public int updateByNicknaem(Member member, String nickname, String username) {
		return memberRepository.updateByNicknameAndUsername(
				member.getNickname(), member.getUsername());
	}
	
	
	// delete - 계정 삭제
	public void deleteMember(Long id) {
		Member find = memberRepository.findById(id).get();
		memberRepository.delete(find);
	}

	
	
}
