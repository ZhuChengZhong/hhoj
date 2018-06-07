package com.hhoj.judger.entity;

public class TestPoint {
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
	public TestPoint(String input, String output) {
		super();
		this.input = input;
		this.output = output;
	}
	
}
