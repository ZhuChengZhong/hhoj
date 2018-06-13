package com.hhoj.judger.entity;

import java.util.List;


public class Message {
	
	
	public static class Point {
		private String input;
		private String output;
		public String getInput() {
			return input;
		}
		public void setInput(String input) {
			this.input = input;
		}
		public String getOutput() {
			return output;
		}
		public void setOutput(String output) {
			this.output = output;
		}
		public Point(String input, String output) {
			super();
			this.input = input;
			this.output = output;
		}
		
	}
	
	
	private Integer submitId;
	private Integer timeLimit;
	private Integer memaryLimit;
	private String language;
	private String code;
	private List<Point>points;
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
	public List<Point> getPoints() {
		return points;
	}
	public void setPoints(List<Point> points) {
		this.points = points;
	}
	
	public Message(Integer submitId, Integer timeLimit, Integer memaryLimit, String language, String code,
			List<Point> points) {
		super();
		this.submitId = submitId;
		this.timeLimit = timeLimit;
		this.memaryLimit = memaryLimit;
		this.language = language;
		this.code = code;
		this.points = points;
	}
	public Message() {
		super();
		// TODO Auto-generated constructor stub
	}

}
