package com.gunr.bookreviewcolumn.bookdata;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
	private Long id;
	
	@Column(nullable=false)
	private String link;
	
	private String image;
	
	private String author;
	
	private double price;
	
	private String publisher;
	
	private String pubdate;
	
	@Column(unique=true, nullable=false)	
	private String isbn;
	
	private String description;
	
	private String title;
	
	@Column(updatable = false)
	private LocalDateTime datatime = LocalDateTime.now();
	
	@ManyToMany
	@JoinColumn(name="member_id")
	private Set<Member> member = new HashSet<>();
	
	@OneToMany
	List<Review> review = new ArrayList<>();
	
	@ManyToOne
	@JoinColumn(name="booktype_id")
	private Big big;
}
