package com.gunr.bookreviewcolumn.member;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface MemberRepository extends JpaRepository<Member, Long>{
	Optional<Member> findByUsername(String username); // 일반회원
	Optional<Member> findByOauthId(String oauthId);  // 소셜회원
	Optional<Member> findById(Long id);
	Optional<Member> findByEmail(String email);
	boolean existsByUsername(String username);
	boolean existsByEmail(String email);
	
	// 활성화된 회원만 이메일 중복확인
	@Query("SELECT COUNT(m) > 0 FROM Member m WHERE m.email = :email AND m.enabled = true")
	boolean existsActiveByEmail(@Param("email") String email);

	// (아이디 찾기) email으로 username 조회
	@Query("select m.username from Member m where m.email= :email")
	String findUsernameByEmail(String email);

	// (비밀번호 찾기)유저네임, 이메일 같으면 패스워드 변경
	@Modifying
	@Transactional
	@Query("update Member m set m.password = :password where m.username = :username and m.email = :email")
	int updatePasswordByUsernameAndEmail(@Param("password") String password,
	                                     @Param("username") String username,
	                                     @Param("email") String email);
	
	// (비밀번호 변경)id, 이전 패스워드가 같으면 패스워드 변경
	@Modifying
	@Transactional
	@Query("update Member m set m.password = :newPassword where m.password = :oldPassword and m.id = :id")
	int updatePasswordById(@Param("newPassword") String newPassword, @Param("oldPassword") String oldPassword, @Param("id") Long id);

	// id 같으면 닉네임 변경
	@Modifying
    @Transactional
    @Query("update Member m set m.nickname= :nickname where m.id= :id")
    int updateByNicknameAndId(@Param("nickname") String nickname,
                              @Param("id") Long id);

    /*
    // id 같으면 유저네임(아이디) 변경
    @Modifying
    @Transactional
    @Query("update Member m set m.username= :username where m.id= :id")
    int updateByUsernameAndId(@Param("username") String username,
                              @Param("id") Long id);
    */
    /*
	// 유저네임이랑 패스워드가 같으면 계정삭제
    @Modifying
    @Transactional
    @Query("delete from Member m where m.password= :password and m.id= :id")
    int deleteByUsernameAndPassword(@Param("password") String password,
                                    @Param("id") Long id);
    */

}
