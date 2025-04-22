package com.gunr.bookreviewcolumn.review;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gunr.bookreviewcolumn.member.Member;
import com.gunr.bookreviewcolumn.member.MemberRepository;

@Service
public class LikeService {
	private final MemberRepository memberRepository;
	private final ReviewRepository reviewRepository;
	
	@Autowired
	public LikeService(MemberRepository memberRepository, ReviewRepository reviewRepository) {
		super();
		this.memberRepository = memberRepository;
		this.reviewRepository = reviewRepository;
	}
	
	//  좋아요추가
	@Transactional
	public void likeReview( Long memberId, Long reviewId) {
		Member member = memberRepository.findById(memberId).orElseThrow( ()-> new RuntimeException("사용자 없음"));
		Review review = reviewRepository.findById(reviewId).orElseThrow( () -> new RuntimeException("리뷰 없음"));
		
		member.getReview().add(review);
		review.getReview_like().add(member);
		
		memberRepository.save(member);  //관계저장
	}
	// 좋아요 갯수
	public int getLikeCount(Long reviewId) {
		Review review=reviewRepository.findById(reviewId).orElseThrow();
		return review.getReview_like().size();
	}
	
	// 좋아요 취소
	@Transactional
	public void unlikeReview( Long userId, Long postId) {
		Member member = memberRepository.findById(userId).orElseThrow();
		Review review = reviewRepository.findById(postId).orElseThrow();
		
		member.getReviewMember().remove(review);  //##
		review.getReview_like().remove(member);  //##
		
		memberRepository.save(member);  //관계저장
	}
}


