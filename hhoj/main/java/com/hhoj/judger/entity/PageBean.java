package com.hhoj.judger.entity;

public class PageBean {
	private Integer size;
	private Integer page;
	private Integer start;
	private Integer count;
	private Integer pageCount;
	public PageBean(Integer size,Integer page,Integer count){
		this.size=size;
		this.page=page;
		this.count=count;
		this.start=size*page;
		this.pageCount=count%size==0?count/size:count/size+1;
	}
	public Integer getSize() {
		return size;
	}
	public void setSize(Integer size) {
		this.size = size;
	}
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public Integer getStart() {
		return start;
	}
	public void setStart(Integer start) {
		this.start = start;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public Integer getPageCount() {
		return pageCount;
	}
	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}
	
	
}
