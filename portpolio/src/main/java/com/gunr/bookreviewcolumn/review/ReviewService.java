package com.gunr.bookreviewcolumn.review;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.gunr.bookreviewcolumn.member.Member;
//import com.gunr.bookreviewcolumn.bookdata.BookRepository;
import com.gunr.bookreviewcolumn.member.MemberRepository;

@Service
public class ReviewService {
	@Autowired ReviewRepository reviewRepository;
	@Autowired MemberRepository memberRepository;
//	@Autowired BookRepository bookRepository;
	
	public Page<Review> getPaging(int page){
		List<Sort.Order> sorts = new ArrayList<>();
		sorts.add(Sort.Order.desc("id"));
		
		Pageable pageable=PageRequest.of(page, 10, Sort.by(sorts));
		return reviewRepository.findAll(pageable);
	}
	
	public List<Review> findAll(){ //전체리스트
		return reviewRepository.findAllByOrderByDesc();
	}
	
	public Page<Review> searchByTitle(String keyword, int page) {
		List<Sort.Order> sorts = new ArrayList<>();
	    sorts.add(Sort.Order.desc("id")); // ID 기준 최신순 정렬
	    Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
	    return reviewRepository.searchByTitle(keyword, pageable);
	}

	public int searchByTitleCount(String keyword) {
	    return reviewRepository.countByTitle(keyword);
	}

	public Member findMemberByUsername(String username) {
	    return memberRepository.findByUsername(username).orElseThrow();
	}
	
	@Transactional
	public Review find(Long id) { //상세보기
		Review review=reviewRepository.findById(id).orElseThrow();

		// medium 리스트 강제 로딩 (LazyInitialization 방지)
	    review.getMedium().size(); 
	    
		return review;
	}
	public void insert(Review review) {
//		review.setMember( memberRepository.findByUsername(review.getMember().getUser()).get() );
//		review.setBookdata( memberRepository.findByUsername(review.getMember().getUsername()).get() );
		
		try { review.setReview_ip(InetAddress.getLocalHost().getHostAddress()); }
		catch (UnknownHostException e) { e.printStackTrace(); }
		reviewRepository.save(review);
//		insert into review (rating, review_content, review_date, review_ip, review_title, bookdata_id, member_id) 
//		values (5, 'review_content' , '24-12-12', '0.0.0.0', 'review_title', 1, 1);
	}
	public Review update_view(Long id) {
		return reviewRepository.findById(id).get();
	}
	
	public int update(Review review) {
		return reviewRepository.updateById(
				review.getId(), review.getRating(), 
				review.getReview_title(), review.getReview_content()
		);
	}
	
	public int delete(Long id) {
		return reviewRepository.deleteReviewById(id);
	}
	
	public List<Review> findByMemberId(Long memberId) {
	    return reviewRepository.findByMemberIdOrderByIdDesc(memberId);
	}

	public List<Review> findMeLikes(Long memberId) {
        return reviewRepository.findMeLikes(memberId);
    }
	
	public List<Review> getTop6RecentReviews() {
	    Pageable pageable = PageRequest.of(0, 6);
	    return reviewRepository.findTop6ByOrderByIdDesc(pageable);
	}
	public List<Review> getTop6ByLikes() {
	    Pageable pageable = PageRequest.of(0, 6);
	    return reviewRepository.findTop6ByLikeCount(pageable);
	}
	
	public Page<Review> findByMediumName(String tagName, int page) {
	    Pageable pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "id"));
	    return reviewRepository.findByMediumName(tagName, pageable);
	}
}
