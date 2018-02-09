package com.hhoj.judge.entity;

import java.util.Date;

/**
 * 用户的一次提交
 * @author zhu
 *
 */
public class Submit {
	// id
	private int sid;
	//提交用户
	private User user;
	//被提交试题
	private Problem problem;
	//运行结果
	private String result;
	//运行使用时间
	private int useTime;
	//运行使用内存
	private int useMemary;
	//提交的代码
	private String code;
	//提交时间
	private Date submitTime;
	//使用语言
	private Language language;
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Problem getProblem() {
		return problem;
	}
	public void setProblem(Problem problem) {
		this.problem = problem;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public int getUseTime() {
		return useTime;
	}
	public void setUseTime(int useTime) {
		this.useTime = useTime;
	}
	public int getUseMemary() {
		return useMemary;
	}
	public void setUseMemary(int useMemary) {
		this.useMemary = useMemary;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Date getSubmitTime() {
		return submitTime;
	}
	public void setSubmitTime(Date submitTime) {
		this.submitTime = submitTime;
	}
	public Language getLanguage() {
		return language;
	}
	public void setLanguage(Language language) {
		this.language = language;
	}
	public Submit() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
