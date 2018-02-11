package com.hhoj.judge.entity;

import java.util.Date;

/**
 * 编程题实体类
 * @author zhu
 *
 */
public class Problem {
	// 问题id
	private int pid;
	//标题
	private String title;
	// 题目描述
	private String desc;
	// 输入示例
	private String inputExample;
	//输出示例
	private String outputExample;
	// 提示
	private String hint;
	// 来源
	private String source;
	// 时间限制
	private String timeLimit;
	//内存限制
	private String memaryLimit;
	// 创建时间
	private Date createTime;
	// 该题通过次数
	private int accepted;
	// 该题提交次数
	private int submited;
	// 题目类型
	private ProblemType type;
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getInputExample() {
		return inputExample;
	}
	public void setInputExample(String inputExample) {
		this.inputExample = inputExample;
	}
	public String getOutputExample() {
		return outputExample;
	}
	public void setOutputExample(String outputExample) {
		this.outputExample = outputExample;
	}
	public String getHint() {
		return hint;
	}
	public void setHint(String hint) {
		this.hint = hint;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getTimeLimit() {
		return timeLimit;
	}
	public void setTimeLimit(String timeLimit) {
		this.timeLimit = timeLimit;
	}
	public String getMemaryLimit() {
		return memaryLimit;
	}
	public void setMemaryLimit(String memaryLimit) {
		this.memaryLimit = memaryLimit;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public int getAccepted() {
		return accepted;
	}
	public void setAccepted(int accepted) {
		this.accepted = accepted;
	}
	public int getSubmited() {
		return submited;
	}
	public void setSubmited(int submited) {
		this.submited = submited;
	}
	public ProblemType getType() {
		return type;
	}
	public void setType(ProblemType type) {
		this.type = type;
	}
	public Problem() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}