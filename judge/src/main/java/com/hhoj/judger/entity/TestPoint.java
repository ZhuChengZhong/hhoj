package com.hhoj.judger.entity;

public class TestPoint {
	//id
	private Integer pointId;
	//测试点所属试题
	private Problem problem;
	//输入
	private String input;
	//输出
	private String output;
	public Integer getPointId() {
		return pointId;
	}
	public void setPointId(Integer pointId) {
		this.pointId = pointId;
	}
	
	public Problem getProblem() {
		return problem;
	}
	public void setProblem(Problem problem) {
		this.problem = problem;
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
	@Override
	public String toString() {
		return "TestPoint [pointId=" + pointId + ", problem=" + problem + ", input=" + input + ", output=" + output
				+ "]";
	}
	
}
