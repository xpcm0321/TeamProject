package com.gunr.bookreviewcolumn.bookdata;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.gunr.bookreviewcolumn.big.Big;
import com.gunr.bookreviewcolumn.member.Member;

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
	
	@ManyToMany
	@JoinTable(name="bookdata_member",
		joinColumns = @JoinColumn(name="bookdata_id"),
		inverseJoinColumns = @JoinColumn(name="member_id")
	)
	private Set<Member> members = new HashSet<>();
	
	@ManyToOne
	private Big big;
	
	// member-bookdata(찜)
	@ManyToMany(mappedBy="bookdata_bookmark")
	private Set<Member> bookmarkPost = new HashSet<>();
}
