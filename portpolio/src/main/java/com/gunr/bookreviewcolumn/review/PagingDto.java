package com.gunr.bookreviewcolumn.review;

import lombok.ToString;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@ToString
@NoArgsConstructor
public class PagingDto {
	private int listtotal;
	private int onepagelist;
	private int pagetotal;
	private int bottomlist;
	
	private int pstartno;
	
	private int start;
	private int current;
	private int end;
	
	public PagingDto(int listtotal, int pstartno) {
		super();
		this.listtotal = listtotal;
		this.pstartno=pstartno*6;
		this.onepagelist=8;
		
		if(this.listtotal <=0) {this.listtotal=1;}
		this.pagetotal = (int) Math.ceil(listtotal/(double)onepagelist);
		
		this.bottomlist=6;
		
		this.current = (this.pstartno/this.onepagelist) + 1;
		this.start = ((this.current-1)/this.bottomlist) * this.bottomlist+1;
		this.end = this.start + this.bottomlist -1;
		
		if(this.pagetotal < this.end) {this.end= this.pagetotal;}
	}
}
