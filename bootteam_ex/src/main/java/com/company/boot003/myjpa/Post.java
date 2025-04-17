package com.company.boot003.myjpa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Post {
	@Id @GeneratedValue
	private Long id;
	private String content;
	
	@ManyToMany(mappedBy = "likePosts") // post가 주인
	private List<User> likedusers = new ArrayList<>();
}
