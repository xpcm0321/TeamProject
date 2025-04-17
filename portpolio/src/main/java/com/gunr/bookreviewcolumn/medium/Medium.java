package com.gunr.bookreviewcolumn.medium;

import java.util.HashSet;
import java.util.Set;

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

	// review-medium
	@ManyToMany(mappedBy="mediums")
	private Set<Review> reviews = new HashSet<>();
	
	@ManyToOne
	private Big big;
}
