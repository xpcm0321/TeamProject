package com.gunr.bookreviewcolumn.big;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.gunr.bookreviewcolumn.bookdata.Bookdata;
import com.gunr.bookreviewcolumn.medium.Medium;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Big {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; // 번호

	@Column(nullable = false)
	private String title; // 제목

	// OneToMany, ManyToMany, ManyToOne
	// big-bookdata
	// #1. big이 삭제될 경우 관련된 bookdata가 medium 남을 위험성이 있음.
	@OneToMany(mappedBy = "big", cascade = CascadeType.ALL)
	private List<Bookdata> bookdata = new ArrayList<>();

	
	// big-medium
	@OneToMany(mappedBy="big", cascade= CascadeType.ALL)
	private List<Medium> medium = new ArrayList<>();
}
