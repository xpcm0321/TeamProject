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
   
   @ManyToMany
   @JoinColumn(name="member_id")
   private Set<Member> member = new HashSet<>();
   
   @ManyToMany
   @JoinColumn(name="medium_id")
   private Set<Medium> medium = new HashSet<>();
   
   @ManyToOne
   @JoinColumn(name="member_id")
   private Member member_r;
   
   @ManyToOne
   @JoinColumn(name="bookdata_id")
   private Bookdata bookdata;


//   private Long review_hit=0L; //조회수
//   
//   private String hashtag; // 태그
//   
//   private Long review_like=0L; // 좋아요
   
//   @ManyToOne
//   private Member member;
}