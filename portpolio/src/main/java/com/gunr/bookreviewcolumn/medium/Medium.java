package com.gunr.bookreviewcolumn.medium;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.gunr.bookreviewcolumn.big.Big;
import com.gunr.bookreviewcolumn.review.Review;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Medium {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;  // 번호
	
	@Column(nullable=false)
	private String name;  // 이름

	// OneToMany, ManyToMany, ManyToOne
	// medium-review
	@ManyToMany(mappedBy="medium")
	private List<Review> review = new ArrayList<>();
	
	
	// medium-big
	@ManyToOne
	private Big big;
}
