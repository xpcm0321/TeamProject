package com.gunr.bookreviewcolumn.review;

import groovy.transform.builder.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BookDto {
	private Long id;
	private String title;
	private String author;
	private String image;
	
	@Builder
	public BookDto(String title) {
		this.title=title;
	}
}
