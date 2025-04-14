package com.gunr.board;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ResultVO {
	private String BuildDate;	//검색 결과를 생성한 시간...? 검색을 시도한 시간?
	private int totalSearch;	//총 검색 결과 개수
	private int startSearch;	//검색 시작 위치
	private int display;			//한 번에 표시할 검색 개수
	private List<BookVO> items;		//개별 검색 결과
}
