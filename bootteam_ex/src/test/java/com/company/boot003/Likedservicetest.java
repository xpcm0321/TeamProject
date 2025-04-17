package com.company.boot003;

import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.company.boot003.myjpa.LikeService;
import com.company.boot003.myjpa.Post;
import com.company.boot003.myjpa.PostRepository;
import com.company.boot003.myjpa.User;
import com.company.boot003.myjpa.UserRepository;

@SpringBootTest
public class Likedservicetest {
	
	
	@Autowired  UserRepository userRepository;
	@Autowired  PostRepository postRepository;
	@Autowired  LikeService    likedService;
	
	@Test
	@Transactional
	public void testLikePost() {
		//사용자생성
		User user = new User(); user.setUsername("KM");
		userRepository.save(user);
		
		//게시글생성
		Post post = new Post(); post.setContent("first board");
		postRepository.save(post);
		
		// 좋아요 증가
		likedService.likePost(user.getId(), post.getId());
		
		// 사용자의 likedPost에서 게시글추가확인
		User updateUser = userRepository.findById(user.getId()).orElseThrow();
		assertTrue(updateUser.getLikedPosts().contains(post));
		
		// 게시글의 likedUsers에서 사용자추가확인
		Post updatePost = postRepository.findById(post.getId()).orElseThrow();
		assertTrue(updatePost.getLikedusers().contains(user));
	}
	
}
