package com.hhoj.judger.entity;

import java.io.Serializable;

public class TestPoint implements Serializable{
	//id
	private Integer pointId;
	//测试点所属试题
	private Integer pid;
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
	

	public Integer getPid() {
		return pid;
	}
	public void setPid(Integer pid) {
		this.pid = pid;
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
		return "TestPoint [pointId=" + pointId + ", pid=" + pid + ", input="
				+ input + ", output=" + output + "]";
	}

	
}
