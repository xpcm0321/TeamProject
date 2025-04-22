package com.gunr.bookreviewcolumn;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.gunr.bookreviewcolumn.bookdata.Bookdata;
import com.gunr.bookreviewcolumn.member.Member;
import com.gunr.bookreviewcolumn.review.Review;
import com.gunr.bookreviewcolumn.review.ReviewRepository;
import com.gunr.bookreviewcolumn.review.ReviewService;

@SpringBootTest
public class TestReview {
	@Autowired ReviewRepository reviewRepository;
	@Autowired ReviewService reviewService;
	
	@Disabled 
	@Test
	public void insertReview() {
		// insert 구문작성?
//		insert into review (rating, review_content, review_date, review_ip, review_title, bookdata_id, member_id) 
//		values (5, 'review_content' , '24-12-12', '0.0.0.0', 'review_title', 1, 1);
		  //120개?
		Member member = new Member();	
		Bookdata bookdata = new Bookdata();
		member.setId(1L);
		bookdata.setId(1L);
	
		Review review = new Review();
		review.setRating(2);
		review.setBookdata(bookdata);
		review.setMember(member);
		review.setReview_title("title1");
		review.setReview_content("content1");
		
		//review.setMembers(member);
		reviewService.insert(review);
		
	}
	@Disabled 
	@Test 
	void findAll() {
		List<Review> list=reviewService.findAll();
		System.out.println(list);
	}
	@Disabled 
	@Test void findId() {
		System.out.println(reviewRepository.findById(1L).get());
	}
	//@Disabled 
	@Test void delete() {
		Review review = new Review(); review.setId(1L);
		reviewService.delete(review);
	}
}
