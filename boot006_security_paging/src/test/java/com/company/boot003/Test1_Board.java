package com.company.boot003;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.company.boot003.board.Board;
import com.company.boot003.board.BoardRepository;
import com.company.boot003.member.Member;
import com.company.boot003.member.MemberRepository;

@SpringBootTest
public class Test1_Board {
	@Autowired BoardRepository boardRepository;
	@Autowired MemberRepository memberRepository;
	
	@Disabled  //@Test
	public void insertMember() {
		Member member = new Member();
		member.setUsername("second");
		memberRepository.save(member);
	}
	
	// 유저(OneToMany:mappdby="member")는 많은 글(ManyToOne)를 쓸수있다.
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
		boardRepository.save(board);
		}
	}
	
	// select * from board
	@Disabled  //@Test
	public void findAllboard() {
		List<Board> list = boardRepository.findAll();
		System.out.println(list.get(0).getBtitle());
	}
	
	// editView / detail
	// select * from board where id=?
	@Disabled  //@Test
	public void findByIdboard() {
		Optional<Board> findBoard = boardRepository.findById(2L);
		if(findBoard.isPresent()) {
			Board board = findBoard.get();
			System.out.println(board.getBtitle());
		}
	}// find - select / save - insert, update / delete - delete
	
	// update board set btitle=?, bcontent=?, btitle=? where id=?
	@Disabled  //@Test
	public void update() {
		Optional<Board> findBoard = boardRepository.findById(2L);
		if(findBoard.isPresent()) {
			Board board = findBoard.get();
			
			board.setBtitle("new-title2");
			board.setBcontent("new-content2");
			boardRepository.save(board);
		}
	}
	
	// delete from board where id=?
	@Disabled  //@Test
	public void delete() {
		Optional<Board> findBoard = boardRepository.findById(2L);
		if(findBoard.isPresent()) {
			Board board = findBoard.get();
			
			boardRepository.delete(board);
		}
	}
}
