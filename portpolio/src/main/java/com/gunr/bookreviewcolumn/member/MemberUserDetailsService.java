package com.gunr.bookreviewcolumn.member;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberUserDetailsService implements UserDetailsService{

	private final MemberRepository memberRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Member> find = memberRepository.findByUsername(username);
		
		if(find.isEmpty()) {
			throw new UsernameNotFoundException("사용자를 확인해주세요.");}
		
		Member member = find.get();
		
		// 관리자 
		List<GrantedAuthority> authorities = new ArrayList<>();
		if("admin".equals(username)) {
			authorities.add(new SimpleGrantedAuthority(MemberRole.ADMIN.getValue()));
		} else {
			authorities.add(new SimpleGrantedAuthority(MemberRole.MEMBER.getValue()));
		}
		return new org.springframework.security.core.userdetails.User(
			    member.getUsername(),
			    member.getPassword() != null ? member.getPassword() : "",
			    member.isEnabled(), // 계정 활성화 여부 enabled 상태 반영
			    true, // 계정 만료 여부
			    true, // 비밀번호 만료 여부
			    true, // 계정 잠금 여부
			    authorities // 권한 정보
			);
	}
}

