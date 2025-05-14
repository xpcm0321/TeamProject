package com.gunr.bookreviewcolumn.review;

import java.time.LocalDateTime;

public class ReviewDto {
	private Long id;
	private String review_title;
	private int rating;
	private Long memberId;
	private LocalDateTime review_date;
	
	public ReviewDto(Long id, String title, int rating, Long memberId, LocalDateTime review_date) {
		this.id = id;
		this.review_title = title;
		this.rating = rating;
		this.memberId = memberId;
		this.review_date = review_date;
	}
	public Long getId() { return id; }
    public String getReview_title() { return review_title; }
    public int getRating() { return rating; }
    public Long getMemberId() { return memberId; }
    public LocalDateTime getReview_date() { return review_date; }
}
