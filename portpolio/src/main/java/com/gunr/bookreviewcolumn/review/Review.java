package com.gunr.bookreviewcolumn.review;

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
   
   // member-review
   @ManyToMany(mappedBy="reviews")
   private Set<Member> members = new HashSet<>();
   
   // review-medium
   @ManyToMany
   @JoinTable(name="review_medium",
   	joinColumns = @JoinColumn(name="review_id"),
   	inverseJoinColumns = @JoinColumn(name="medium_id")
   )
   private Set<Medium> mediums = new HashSet<>();
   
   // member-review(좋아요)
   @ManyToMany
   @JoinTable(name = "likes",
   	joinColumns = @JoinColumn(name = "review_id"),
   	inverseJoinColumns = @JoinColumn(name = "member_id")
   )
   private Set<Member> review_like = new HashSet<>();
}