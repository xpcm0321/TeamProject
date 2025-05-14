package com.gunr.bookreviewcolumn.bookdata;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookdataDto {
	private Long id;
	private String title;
	private String author;
	private String publisher;
	private String pubdate;
	private String image;
	private String link;
	private String isbn;
	
}
