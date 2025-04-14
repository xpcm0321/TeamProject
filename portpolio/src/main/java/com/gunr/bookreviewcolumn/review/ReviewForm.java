package com.gunr.bookreviewcolumn.review;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class ReviewForm {
	@NotEmpty(message = "리뷰 제목을 작성해주세요.")
	@Size(max=200, min=1)
	private String review_title;
	
	@NotEmpty(message = "리뷰 내용을 작성해주세요.")
	@Size(max=1000, min=1)
	private String review_content;
}
