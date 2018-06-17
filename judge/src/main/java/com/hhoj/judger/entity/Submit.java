package com.hhoj.judger.entity;

import java.util.List;

import com.alibaba.fastjson.JSON;

public class Submit {
	private Integer submitId;
	private Integer timeLimit;
	private Integer memaryLimit;
	private String language;
	private String code;
	private List<TestPoint>points;
	public Integer getSubmitId() {
		return submitId;
	}
	public void setSubmitId(Integer submitId) {
		this.submitId = submitId;
	}
	public Integer getTimeLimit() {
		return timeLimit;
	}
	public void setTimeLimit(Integer timeLimit) {
		this.timeLimit = timeLimit;
	}
	public Integer getMemaryLimit() {
		return memaryLimit;
	}
	public void setMemaryLimit(Integer memaryLimit) {
		this.memaryLimit = memaryLimit;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public List<TestPoint> getPoints() {
		return points;
	}
	public void setPoints(List<TestPoint> points) {
		this.points = points;
	}
	
	public Submit(Integer submitId, Integer timeLimit, Integer memaryLimit, String language, String code,
			List<TestPoint> points) {
		super();
		this.submitId = submitId;
		this.timeLimit = timeLimit;
		this.memaryLimit = memaryLimit;
		this.language = language;
		this.code = code;
		this.points = points;
	}
	public static void main(String[] args) {
		/*List<TestPoint>points=new ArrayList<>();
		TestPoint p1=new TestPoint("1 2","a,b");
		TestPoint p2=new TestPoint("3 4","c,d");
		points.add(p1);
		points.add(p2);
		Submit submit=new Submit(1, 1000, 1000,"java","printf true", points);
		JSONObject json=new JSONObject();
		String s=JSON.toJSONString(submit);
		System.out.println(s);*/
		Submit a=JSON.parseObject("11",Submit.class);
		System.out.println(a);
		
	}
}
