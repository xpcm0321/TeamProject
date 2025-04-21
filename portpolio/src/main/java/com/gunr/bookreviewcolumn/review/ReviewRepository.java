package com.gunr.bookreviewcolumn.review;

import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gunr.bookreviewcolumn.medium.Medium;

public interface ReviewRepository extends JpaRepository<Review, Long> {
	Page<Review> findAll(Pageable pageable);
	
	@Query("select r from Review r order by id desc")
	List <Review> findAllByOrderByDesc();
	
	@Modifying
	@Transactional
	@Query("update Review r set r.rating= :rating, r.review_title= :review_title, r.review_content= :review_content"
			+ " where r.id= :id")
	int updateById(Long id, int rating, String review_title, String review_content);
	
	@Modifying
	@Transactional
	@Query("delete from Review r where r.id= :id") 
	int deleteReviewById(@Param("id") Long id);
}
