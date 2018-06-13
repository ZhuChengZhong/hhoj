package com.hhoj.judger.entity;

public class JudgeResult {
	/**
	 * 提交id
	 */
	private Integer submitId;
	/**
	 * 判题结果 
	 */
	private String result;
	/**
	 * 使用时间
	 */
	private Integer useTime;
	/**
	 * 使用内存
	 */
	private Integer useMemary;
	public Integer getSubmitId() {
		return submitId;
	}
	public void setSubmitId(Integer submitId) {
		this.submitId = submitId;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public Integer getUseTime() {
		return useTime;
	}
	public void setUseTime(Integer useTime) {
		this.useTime = useTime;
	}
	public Integer getUseMemary() {
		return useMemary;
	}
	public void setUseMemary(Integer useMemary) {
		this.useMemary = useMemary;
	}
	@Override
	public String toString() {
		return "JudgeResult [submitId=" + submitId + ", result=" + result + ", useTime=" + useTime + ", useMemary="
				+ useMemary + "]";
	}
	
}
