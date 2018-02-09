package com.hhoj.judge.entity;

public class TestPoint {
	//id
	private int pointId;
	//测试点所属试题
	private int Problem;
	//输入
	private String input;
	//输出
	private String output;
	public int getPointId() {
		return pointId;
	}
	public void setPointId(int pointId) {
		this.pointId = pointId;
	}
	public int getProblem() {
		return Problem;
	}
	public void setProblem(int problem) {
		Problem = problem;
	}
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
	
}
