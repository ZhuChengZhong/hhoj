package com.hhoj.judger.entity;

import java.io.Serializable;

/**
 * 竞赛试题类
 * @author zhu
 *
 */
public class ContestProblem implements Serializable{
	/**
	 * id
	 */
	private Integer cpId;
	/**
	 * 具体试题类
	 */
	private Problem problem;
	/**
	 * 竞赛实体类id
	 */
	private Integer contestId;
	/**
	 * 试题的提交次数
	 */
	private Integer submited;
	/**
	 * 该竞赛试题通过次数
	 */
	private Integer accepted;
	public Integer getCpId() {
		return cpId;
	}
	public void setCpId(Integer cpId) {
		this.cpId = cpId;
	}
	public Problem getProblem() {
		return problem;
	}
	public void setProblem(Problem problem) {
		this.problem = problem;
	}
	public Integer getContestId() {
		return contestId;
	}
	public void setContestId(Integer contestId) {
		this.contestId = contestId;
	}
	
	public Integer getSubmited() {
		return submited;
	}
	public void setSubmited(Integer submited) {
		this.submited = submited;
	}
	public Integer getAccepted() {
		return accepted;
	}
	public void setAccepted(Integer accepted) {
		this.accepted = accepted;
	}
	
}
