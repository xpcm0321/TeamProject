package com.gunr.bookreviewcolumn.member;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
		
		if(find.isEmpty()) {throw new UsernameNotFoundException("사용자를 확인해주세요.");}
		
		Member member = find.get();
		
		
		
		
		
		return null;
	}

}
