package com.gunr.board;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class BookVO {
	private String title;		//제목
	private String link;		//링크
	private String image;		//표지 이미지
	private String author;		//저자명
	private String price;	//가격
	private String publisher;	//출판사
	private String pubdate;		//출간일자
	private String isbn;		//ISBN
	private String description;	//책 소개
}
