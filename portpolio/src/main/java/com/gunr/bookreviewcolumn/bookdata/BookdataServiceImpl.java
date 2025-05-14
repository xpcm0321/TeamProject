package com.gunr.bookreviewcolumn.bookdata;

import java.util.HashMap;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BookdataServiceImpl implements BookdataService{
	@Autowired BookdataRepository bookdataRepository;	// 데이터베이스 연동을 위한 리포지토리
	
    @Autowired  // Spring이 자동으로 BookDataRepository를 주입
    public BookdataServiceImpl(BookdataRepository bookDataRepository) {
        this.bookdataRepository = bookDataRepository;  // 생성자를 통해 BookDataRepository 주입
    }

    // 실제 책 데이터를 데이터베이스에서 가져오는 추상 메서드 구현
    @Override
    public List<Bookdata> getBookdataFromDatabase() {
        // bookDataRepository는 데이터베이스에서 모든 책 데이터를 가져옴
        return bookdataRepository.findAll();
    }
	
	@Override
	public List<Bookdata> findAll() {
		return bookdataRepository.findAll();
	}

	@Transactional
	@Override
	public HashMap<String, String> findTitle(String title) {
		HashMap<String, String> book = new HashMap<>(); //HashMap<자료형,자료형> map = new HashMap<>();
		return book;
	}

	@Transactional
	@Override
	public Bookdata findAuthor(String author) {
		Bookdata book = new Bookdata();
		return book;
	}

	@Override
	public void insert(Bookdata book) {
		book.getTitle();
		bookdataRepository.save(book);
	}

	@Override
	public void delete(Bookdata book) {
		bookdataRepository.delete(book);
	}

	@Override
	public void save(BookdataDto dto) {
		Bookdata book = new Bookdata();
		book.setTitle(dto.getTitle());
		book.setAuthor(dto.getAuthor());
		book.setPublisher(dto.getPublisher());
		book.setPubdate(dto.getPubdate());
		book.setImage(dto.getImage());
		book.setLink(dto.getLink());
		book.setIsbn(dto.getIsbn());
		bookdataRepository.save(book);
	}
	
	// 오버로딩된 메서드: Bookdata 자체 저장
	@Override
	public void save(Bookdata book) {
		bookdataRepository.save(book);
		
	}

	@Override
	public Bookdata saveOrFind(BookdataDto dto) {
		return bookdataRepository.findByTitle(dto.getTitle())
				.orElseGet(() -> {
					Bookdata newBook = new Bookdata();
					newBook.setTitle(dto.getTitle());
					newBook.setAuthor(dto.getAuthor());
					newBook.setImage(dto.getImage());
					newBook.setIsbn(dto.getIsbn());
					newBook.setLink(dto.getLink());
					return bookdataRepository.save(newBook);
				});
	}

	@Override
	public Page<Bookdata> BookdataList(Pageable pageable) {
		return bookdataRepository.findAll(pageable);
	}
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

X

**read** 전체 책 데이터 확인, 상세 데이터 확인
특정 제목의 책들을 조회할 때 - select * from bookdata where title=? and publisher=?

X

X
 */