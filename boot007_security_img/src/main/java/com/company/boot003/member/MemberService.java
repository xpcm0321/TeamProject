package com.company.boot003.member;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service  //##1
@RequiredArgsConstructor
public class MemberService {
	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;  // SecurityConfig
	
	//insert
	public Member insertMember(Member member) {
		//     셋팅            암호화                   비밀번호
		member.setPassword(passwordEncoder.encode( member.getPassword() ));
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
	
	//update / updatePass
	public int updateByPass(Member member, String old) {
		return memberRepository.updateByIdAndPassword(
					member.getPassword(), old, member.getId()
				);
	}
	public Member updateByEmail(Member member) {
		Member find = memberRepository.findById(member.getId()).get();
		find.setEmail(member.getEmail());
		return memberRepository.save(find);
	}
	
	//delete
	public void deleteMember(Long id) {
		Member find = memberRepository.findById(id).get();
		memberRepository.delete(find);
	}
	
	
}
