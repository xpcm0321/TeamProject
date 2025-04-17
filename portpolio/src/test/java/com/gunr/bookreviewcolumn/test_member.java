package com.gunr.bookreviewcolumn;

import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.gunr.bookreviewcolumn.member.Member;
import com.gunr.bookreviewcolumn.member.MemberRepository;


@SpringBootTest
public class test_member {
	@Autowired MemberRepository memberRepository;
	
	@Disabled  //@Test
	public void insert() {
		Member member = new Member();
		member.setUsername("user2");
		member.setNickname("nickname2");
		member.setPassword("2222");
		member.setMemberimg("img2");
		member.setEmail("user2@gmail.com");
		member.setAge(22);
		memberRepository.save(member);
	}
	
	@Disabled  //@Test
	public void findAll() {
		List<Member> list = memberRepository.findAll();
		System.out.println(list);
	}
	
	@Disabled  //@Test
	public void findUsername() {
		System.out.println(memberRepository.findByUsername("user1").get());
	}
	
	/*
	@Disabled  //@Test
	public void update() {
		Member member = memberRepository.findByUsername("user1").get();
		member.setNickname("nick-new");
		memberRepository.save(member);
	}*/
	
	@Disabled  //@Test
	public void updatePassword() {
		memberRepository.updateByPasswordAndUsername("11111", "1111", "user1");
	}
	
	@Disabled  //@Test
	public void updateNickname() {
		memberRepository.updateByNicknameAndUsername("nickname-new", "user1");
	}
	
	@Test
	public void delete() {
		memberRepository.deleteByUsernameAndPassword("user2", "2222");
	}
}
