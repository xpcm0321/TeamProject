package com.gunr.bookreviewcolumn.bookdata;

import java.util.HashMap;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookdataService {
	public List<Bookdata> findAll();		//전체책데이터조회
	public HashMap<String, String> findTitle(String title);		//제목으로 책 찾기
	public Bookdata findAuthor(String author);		//저자명으로 책 찾기
	List<Bookdata> getBookdataFromDatabase();
	
	public void insert(Bookdata book);  // 책 등록
	public void delete(Bookdata book);  // 책 삭제
	public void save(BookdataDto dto);  // DTO로 저장
	public void save(Bookdata book);    // Entity로 저장 (오버로딩)
	public Bookdata saveOrFind(BookdataDto dto);  // 이미 존재하면 반환, 없으면 저장
	
	// 페이지네이션 리스트
	Page<Bookdata> BookdataList(Pageable pageable);
}
/*
**create** 책 데이터 등록
insert into bookdata (title, link, image, author, discount, publisher, pubdate, isbn, description) values (?, ?, ?, ?, ?, ?, ?, ?, ?)

**read** 전체 책 데이터 확인, 상세 데이터 확인
전체 데이터 확인만 할 때 - select * from bookdata

특정 제목의 책들을 조회할 때 - select * from bookdata where title=?
select * from bookdata where title=? and publisher=?

**delete** 책 데이터 삭제
delete from bookdata where title=? and publisher=?
*/