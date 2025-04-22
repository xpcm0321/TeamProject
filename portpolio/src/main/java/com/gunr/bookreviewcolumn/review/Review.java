package com.gunr.bookreviewcolumn.review;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.gunr.bookreviewcolumn.bookdata.Bookdata;
import com.gunr.bookreviewcolumn.medium.Medium;
import com.gunr.bookreviewcolumn.member.Member;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Review {
   @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;
   
   private int rating=1; //별점

   @Column(nullable=false, length=200)
   private String review_title; //리뷰제목
   
   @Column(columnDefinition = "text", nullable=false)
   private String review_content; //리뷰내용
   
   @Column(nullable=false)
   private String review_ip; //ip주소
   
   @Column(nullable=false)
   private LocalDateTime review_date=LocalDateTime.now(); //작성일
   
   // OneToMany, ManyToMany, ManyToOne
   // review-member
   @ManyToMany(mappedBy="review_m")  // Member.class에서 private List<Review> review_m = new ArrayList<>();
   private List<Member> member_r = new ArrayList<>();
   
   // review-member
   @ManyToOne
   @JoinColumn(name="member_id")
   private Member member;  // review
   
   // review-member(좋아요)
   @ManyToMany
   @JoinTable(name = "likes",
	   joinColumns = @JoinColumn(name = "review_id"),
	   inverseJoinColumns = @JoinColumn(name = "member_id")
	)
   private List<Member> review_like = new ArrayList<>();
   
   
   // review-medium
   @ManyToMany
   @JoinTable(name="review_medium",
   	joinColumns = @JoinColumn(name="review_id"),
   	inverseJoinColumns = @JoinColumn(name="medium_id")
   )
   private List<Medium> medium = new ArrayList<>();
   
   
   // review-bookdata
   @ManyToOne
   @JoinColumn(name="bookdata_id")
   private Bookdata bookdata;
}