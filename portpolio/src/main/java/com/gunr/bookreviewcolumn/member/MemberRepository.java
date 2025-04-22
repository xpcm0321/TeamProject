package com.gunr.bookreviewcolumn.member;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface MemberRepository extends JpaRepository<Member, Long>{
	Optional<Member> findByUsername(String username);
	Optional<Member> findByEmail(String email);
	
	
	// 유저네임, 패스워드 같으면 패스워드 변경
	@Modifying
	@Transactional
	@Query("update Member m set m.password= :password where m.password= :old and m.username= :username")
	int updateByPasswordAndUsername(String password, String old, String username);
	
	// 유저네임 같으면 닉네임 변경
	@Modifying
	@Transactional
	@Query("update Member m set m.nickname= :nickname where m.username= :username")
	int updateByNicknameAndUsername(String nickname, String username);


	// 유저네임이랑 패스워드가 같으면 계정삭제
	@Modifying
	@Transactional
	@Query("delete from Member m where m.username= :username and m.password= :password")
	int deleteByUsernameAndPassword(String username, String password);

}
