package com.company.boot004;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.company.boot004.member.Member;
import com.company.boot004.member.MemberRepository;

@SpringBootTest
public class Test1_member { 
	@Autowired MemberRepository memberRepository;
	
	// insert, update - save
	// select - findAll , findById
	// delete - delete
	
	@Disabled @Test
	public void insert() {
		Member member = new Member();
		member.setUsername("user1"); 
		member.setPassword("1111"); 
		member.setEmail("user1@gmail.com");
		memberRepository.save(member);
	}
	
	@Disabled @Test void findAll() {
		List<Member> list = memberRepository.findAll();
		System.out.println(list);
	}
	
	@Disabled @Test void findId() {
		System.out.println(memberRepository.findById(1L).get());
	}
	
	@Disabled @Test void findUsername() {
		System.out.println(memberRepository.findByUsername("user1-new").get());
	}
	
	@Disabled @Test void update() {
		Member member = memberRepository.findById(2L).get();
		member.setUsername("user2-new");
		memberRepository.save(member);
	}
	
	@Disabled @Test void updatePassword() { 
		memberRepository.updateByIdAndPassword("1234", "2222", 2L);
	}
	
	@Disabled @Test void delete() {
		Member member = memberRepository.findById(2L).get();
		memberRepository.delete(member);
	}
}
