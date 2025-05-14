package com.gunr.bookreviewcolumn.member;

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
import javax.persistence.OneToMany;

import com.gunr.bookreviewcolumn.bookdata.Bookdata;
import com.gunr.bookreviewcolumn.image.Image;
import com.gunr.bookreviewcolumn.review.Review;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor // jpa용
@AllArgsConstructor // 테스트 유틸용
@Builder // 객체 생성 쉽게
public class Member {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; // 번호

	@Column(unique = true)
	private String username; // 아이디

	@Column(nullable = false)
	private String nickname; // 닉네임

	@Column(nullable = false)
	private String password; // 비밀번호

	@Column(nullable = false)
	private int age; // 나이
	
	@Column
	private String provider;  // kakao , naver ##
	
	@Column
	private String oauthId;  // 소셜 로그인에서 받은 고유 사용자 id ##

	@Column
	private String accessToken;
	
	// 추가
	@Column(nullable = false)
	private String memberimg=""; // 프로필 사진 선택

	@Column(unique = true, nullable = false)
	private String email; // 이메일

	@Column(updatable = false, nullable = false)
	private LocalDateTime datetime = LocalDateTime.now(); // 현재날짜

	// OneToMany, ManyToMany, ManyToOne
	// member-bookdata
	@ManyToMany(mappedBy = "member")
	private List<Bookdata> bookdata = new ArrayList<>();

	// member - bookdata(찜)
	@ManyToMany
	@JoinTable(name = "bookmark",
	joinColumns = @JoinColumn(name = "member_id"),
	inverseJoinColumns = @JoinColumn(name = "bookdata_id")
			)
	private List<Bookdata> bookdata_bookmark = new ArrayList<>(); // bookdata bookmark
	
	
	// member-review
	@ManyToMany
	@JoinTable(name = "member_review",
		joinColumns = @JoinColumn(name = "member_id"),
		inverseJoinColumns = @JoinColumn(name = "review_id")
	)
	private List<Review> review_m = new ArrayList<>();   // member - review
	
	// member-review
	@OneToMany(mappedBy="member")
	List<Review> review = new ArrayList<>(); // member

	// member - review(좋아요)
	@ManyToMany(mappedBy = "review_like")
	private List<Review> reviewMember = new ArrayList<>();
	
	
	// member-image
	@ManyToOne
	private Image image;
	
	@Column(nullable = false)
	private boolean enabled = true; // 회원탈퇴시 비활성화
}
