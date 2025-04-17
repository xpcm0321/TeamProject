package com.company.boot003;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.company.boot003.board.Board;
import com.company.boot003.board.BoardService;
import com.company.boot003.member.Member;
import com.company.boot003.member.MemberRepository;
import com.company.boot003.member.MemberService;



@SpringBootTest
public class Test2_memberservice { 
	@Autowired MemberRepository memberRepository;
	@Autowired MemberService memberService;
	@Autowired BoardService boardService;
	
	// insert, update - save
	// select - findAll , findById
	// delete - delete
	
	@Disabled  //@Test
	void insert() {
		Member member = new Member();
		member.setUsername("user2"); 
		member.setPassword("2222"); 
		member.setEmail("user2@gmail.com");
		System.out.println(memberService.insertMember(member)); 
	}
	/*
	@Disabled  //@Test
	public void insertboard() {
		for(int i=0; i<120; i++) {
		Member member = new Member(); 
		member.setUsername("1111"); // 있는 username
		Board board = new Board();
		board.setBtitle("title-paging"); board.setBcontent("content-paging");
		board.setBpass("1111"); board.setBfile("2.jpg");
		try {
			board.setBip(InetAddress.getLocalHost().getHostAddress());
		} catch (UnknownHostException e) { e.printStackTrace(); }
		board.setMember(member);
		boardService.insert(board);
		}
	}*/
}
