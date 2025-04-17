package com.company.boot003.member;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface MemberRepository extends JpaRepository<Member, Long>{
	Optional<Member> findByUsername(String username);
	// 아이디와 비번이 같은 유저의 비밀번호 변경
	@Modifying
	@Transactional 
	@Query("update Member m set m.password= :password where m.password= :old and m.id= :id")
	int updateByIdAndPassword(String password, String old, Long id);
	
	
	
}
/*
crud - sql 구문적기
insert into member (username, password, email) values(?,?,?) - save
select * from member            - findAll
select * from member where id=? - findById
update member set username=?, password=?, email=? where id=? - save
delete from member where id=?

mysql> desc member;
+----------+--------------+------+-----+---------+----------------+
| Field    | Type         | Null | Key | Default | Extra          |
+----------+--------------+------+-----+---------+----------------+
| id       | bigint       | NO   | PRI | NULL    | auto_increment |
| email    | varchar(255) | YES  | UNI | NULL    |                |
| password | varchar(255) | YES  |     | NULL    |                |
| udate    | datetime(6)  | YES  |     | NULL    |                |
| username | varchar(255) | YES  | UNI | NULL    |                |
+----------+--------------+------+-----+---------+----------------+
5 rows in set (0.00 sec)
*/
