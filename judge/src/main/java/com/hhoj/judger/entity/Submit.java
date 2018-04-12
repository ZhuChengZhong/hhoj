package com.hhoj.judger.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户的一次提交
 * @author zhu
 *
 */
public class Submit implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// id
	private Integer sid;
	//提交用户
	private transient User user;
	//被提交试题
	private transient Problem problem;
	//运行结果
	private String result;
	//运行使用时间
	private Integer useTime;
	//运行使用内存
	private Integer useMemary;
	//提交的代码
	private String code;
	//提交时间
	private Date submitTime;
	//使用语言
	private transient Language language;
	//记录该提交是否被评测过    1 已评测 0 未评测
	private Integer judged;
	//是否为比赛的提交
	private Integer isContest;
	
	public Integer getIsContest() {
		return isContest;
	}
	public void setIsContest(Integer isContest) {
		this.isContest = isContest;
	}
	public Integer getJudged() {
		return judged;
	}
	public void setJudged(Integer judged) {
		this.judged = judged;
	}
	public Integer getSid() {
		return sid;
	}
	public void setSid(Integer sid) {
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
	
	@Override
	public String toString() {
		return "Submit [sid=" + sid + ", user=" + user + ", problem=" + problem + ", result=" + result + ", useTime="
				+ useTime + ", useMemary=" + useMemary + ", code=" + code + ", submitTime=" + submitTime + ", language="
				+ language + ", judged=" + judged + ", isContest=" + isContest + "]";
	}
	public Submit(User user, Problem problem, String result, Integer useTime, Integer useMemary, String code,
			Date submitTime, Language language, Integer judged, Integer isContest) {
		super();
		this.user = user;
		this.problem = problem;
		this.result = result;
		this.useTime = useTime;
		this.useMemary = useMemary;
		this.code = code;
		this.submitTime = submitTime;
		this.language = language;
		this.judged = judged;
		this.isContest = isContest;
	}
	
	
}
