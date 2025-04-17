package com.company.boot003.myjpa;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikeService {
	private final UserRepository userRepository;
	private final PostRepository postRepository;
	
	@Autowired
	public LikeService(UserRepository userRepository, PostRepository postRepository) {
		super();
		this.userRepository = userRepository;
		this.postRepository = postRepository;
	}
	
	@Transactional
	public void likePost(Long userId, Long postId) {
		User user = userRepository.findById(userId).orElseThrow( ()-> new RuntimeException("사용자 없음") );
		Post post = postRepository.findById(postId).orElseThrow( ()-> new RuntimeException("게시글 없음") );
		
		user.getLikedPosts().add(post);
		post.getLikedusers().add(user);
		
		userRepository.save(user);  //관계저장
	}
	
	// 좋아요 갯수
	public int getLikeCount(Long postId) {
		Post post = postRepository.findById(postId).orElseThrow();
		return post.getLikedusers().size();
		
	}
	// 좋아요 취소
		public void unLikeCount(Long userId, Long postId) {
			User user = userRepository.findById(userId).orElseThrow();
			Post post = postRepository.findById(postId).orElseThrow();
			
			user.getLikedPosts().remove(post);
			post.getLikedusers().remove(user);
			
			userRepository.save(user);  //관계저장 
			
		}
	
}
