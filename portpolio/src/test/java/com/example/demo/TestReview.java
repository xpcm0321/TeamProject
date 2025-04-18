package com.example.demo;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.gunr.bookreviewcolumn.member.Member;
import com.gunr.bookreviewcolumn.review.Review;
import com.gunr.bookreviewcolumn.review.ReviewRepository;
import com.gunr.bookreviewcolumn.review.ReviewService;

@SpringBootTest
public class TestReview {
	@Autowired ReviewRepository reviewRepository;
	@Autowired ReviewService reviewService;
	
	@Disabled @Test
	public void insertReview() {
		for(int i=0; i<120; i++) {
			Member member = new Member();	
			member.setUsername("test");
			Review review = new Review();
			review.setReview_title("title1");
			review.setReview_content("content1");
			try {
				review.setReview_ip(InetAddress.getLocalHost().getHostAddress());
			} catch (UnknownHostException e) { e.printStackTrace(); }
			//review.setMembers(member);
			reviewRepository.save(review);
		}
	}
	@Disabled @Test void findAll() {
		List<Review> list=reviewRepository.findAll();
		System.out.println(list);
	}
	@Disabled @Test void findId() {
		System.out.println(reviewRepository.findById(1L).get());
	}
	@Test void delete() {
		Review review = reviewRepository.findById(1L).get();
		reviewRepository.delete(review);
	}
}
