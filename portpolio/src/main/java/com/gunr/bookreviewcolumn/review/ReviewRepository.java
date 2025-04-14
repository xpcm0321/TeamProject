package com.gunr.bookreviewcolumn.review;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewRepository extends JpaRepository<Review, Long> {
	@Query("select r from Review r order by id desc")
	List <Review> findAllByOrderByDesc();
	
	@Modifying
	@Transactional
	@Query("update Review r set r.rating= :rating, r.review_title= :title, r.review_content= :content where r.id= :id")
	int updateById(Long id, int rating, String review_title, String review_content);
	
	@Modifying
	@Transactional
	@Query("delete from Review r where r.id= :id")
	int deleteById(@Param("id") Long id);
}
