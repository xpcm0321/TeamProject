package com.gunr.bookreviewcolumn.big;

import java.util.ArrayList;
import java.util.List;

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
	private Long id;
	
	@Column(nullable=false)
	private String title;
	
	@OneToMany
	List<Bookdata> bookdata = new ArrayList<>();
	
	@OneToMany
	List<Medium> medium = new ArrayList<>();

}
