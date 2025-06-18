package com.gunr.bookreviewcolumn.image;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.gunr.bookreviewcolumn.member.Member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor // jpa용
@Entity
@Getter
@Setter
public class Image {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;  // 번호
	
	private String img;
	
	// OneToMany, ManyToMany, ManyToOne
	// image-member
	@OneToMany(mappedBy="image")
	private List<Member> member = new ArrayList<>();
	
	
	public Image(String img) {
	        this.img = img;
	}
}
