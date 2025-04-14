package com.gunr.bookreviewcolumn.review;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Review {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String review_ip; //ip주소
	private int rating=1; //별점
	@Column(length=200)
	private String review_title; //리뷰제목
	@Column(columnDefinition = "text")
	private String review_content; //리뷰내용
	private LocalDateTime review_date=LocalDateTime.now(); //작성일
	private Long review_hit=0L; //조회수
	
//	@ManyToOne
//	private Member member;
}
