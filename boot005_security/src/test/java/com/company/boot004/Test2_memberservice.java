package com.company.boot004;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.company.boot004.member.Member;
import com.company.boot004.member.MemberRepository;
import com.company.boot004.member.MemberService;

@SpringBootTest
public class Test2_memberservice { 
	@Autowired MemberRepository memberRepository;
	@Autowired MemberService memberService;
	
	// insert, update - save
	// select - findAll , findById
	// delete - delete
	
	@Test
	void insert() {
		Member member = new Member();
		member.setUsername("user2"); 
		member.setPassword("2222"); 
		member.setEmail("user2@gmail.com");
		System.out.println(memberService.insertMember(member)); 
	}

}
