package com.gunr.bookreviewcolumn.member;

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
import javax.persistence.OneToMany;

import com.gunr.bookreviewcolumn.bookdata.Bookdata;
import com.gunr.bookreviewcolumn.review.Review;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Member {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique=true)
	private String username;
	
	@Column(nullable=false)
	private String nickname;
	
	@Column(nullable=false)
	private String password;
	
	private int age;
	
	@Column(unique=true, nullable=false)
	private String email;
	
	@Column(updatable = false, nullable=false)
	private LocalDateTime datetime = LocalDateTime.now();
	
	@ManyToMany
	@JoinColumn(name="bookdata_id")
	private Set<Bookdata> bookdata = new HashSet<>();
	
	@ManyToMany
	@JoinColumn(name="review_id")
	private Set<Review> review = new HashSet<>();
	
	@OneToMany
	List<Review> review_b = new ArrayList<>();
	
}
