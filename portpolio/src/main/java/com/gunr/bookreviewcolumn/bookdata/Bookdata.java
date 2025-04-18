package com.gunr.bookreviewcolumn.bookdata;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.gunr.bookreviewcolumn.big.Big;
import com.gunr.bookreviewcolumn.member.Member;
import com.gunr.bookreviewcolumn.review.Review;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Bookdata {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;  // 번호
	
	@Column(nullable=false)
	private String link;  // 링크
	
	private String image;  // 이미지
	
	private String author;  // 작가
	
	private double price;  // 가격
	
	private String publisher;  // 출판사
	
	private String pubdate;  // 출판날짜
	
	@Column(unique=true, nullable=false)	
	private String isbn;  // isbn
	
	private String description;  // 책설명
	
	private String title;  // 책 제목
	
	@Column(updatable = false)
	private LocalDateTime datatime = LocalDateTime.now();  // 관리자가 등록한 날짜
	
	// OneToMany, ManyToMany, ManyToOne
	// bookdata-member
	@ManyToMany
	@JoinTable(name="bookdata_member",
		joinColumns = @JoinColumn(name="bookdata_id"),
		inverseJoinColumns = @JoinColumn(name="member_id")
	)
	private List<Member> member = new ArrayList<>();
	
	// bookdata-member(찜)
	@ManyToMany(mappedBy="bookdata_bookmark")
	private List<Member> bookmarkPost = new ArrayList<>();
	
	
	// bookdata-big
	//#2. 성능최적화
	@ManyToOne(fetch=FetchType.LAZY)
	private Big big;
	
	
	// bookdata-review
	@OneToMany(mappedBy="bookdata")
	List<Review> review = new ArrayList<>();
	
}
