package com.gunr.bookreviewcolumn.review;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ReviewForm {
	@NotEmpty(message = "리뷰할 책을 선택해주세요.")
	private String bookdata;
	
	@NotEmpty(message = "카테고리를 선택해주세요.")
	private String medium;
	
	@NotEmpty(message = "리뷰 제목을 작성해주세요.")
	@Size(max=200, min=1)
	private String review_title;
	
	@NotEmpty(message = "리뷰 내용을 작성해주세요.")
	@Size(max=1000, min=1)
	private String review_content;
	
	
}
