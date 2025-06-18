package com.gunr.bookreviewcolumn.review;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewRepository extends JpaRepository<Review, Long> {
	Page<Review> findAll(Pageable pageable);
	
	// 메인에 띄울 최신 리뷰
	@Query("select r from Review r order by r.id desc")
	List<Review> findTop6ByOrderByIdDesc(Pageable pageable);
	
	// 메인에 띄울 좋아요 많은 리뷰
	@Query("select r from Review r left join r.review_like l group by r.id order by count(l) desc")
	List<Review> findTop6ByLikeCount(Pageable pageable);
	
	// 제목에 키워드가 포함된 리뷰 목록 페이징
	@Query("select r from Review r where r.review_title like %:keyword%")
	Page<Review> searchByTitle(@Param("keyword") String keyword, Pageable pageable);
    
    // 제목에 키워드가 포함된 리뷰 수
	@Query("select count(r) from Review r where r.review_title like %:keyword%")
	int countByTitle(@Param("keyword") String keyword);
	
	// 모든 유저 리뷰 리스트
	@Query("select r from Review r order by id desc")
	List <Review> findAllByOrderByDesc();
	
	// 내가 좋아요 한 리뷰
	@Query("select r from Review r join r.review_like rl where rl.id= :memberId" ) 
	List<Review> findMeLikes(@Param("memberId") Long memberId);

	// 태그 리스트
	@Query("select r from Review r join r.medium m where m.name = :name order by r.id desc")
	Page<Review> findByMediumName(@Param("name") String name, Pageable pageable);
	
	List<Review> findByMemberIdOrderByIdDesc(Long memberId);
	
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
