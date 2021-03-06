package com.hhoj.judger.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 编程题实体类
 * @author zhu
 *
 */
public class Problem implements Serializable{
	
	public static final int PASS=1;
	public static final int NO_PASS=0;
	// 问题id
	private Integer pid;
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
	private Integer timeLimit;
	//内存限制
	private Integer memaryLimit;
	// 创建时间
	private Date createTime;
	// 该题通过次数
	private Integer accepted;
	// 该题提交次数
	private Integer submited;
	// 题目类型
	private ProblemType type;
	//该题是否发布（0未发布 1发布）
	private Integer publish;
	//标记用户是否通过 1通过 0未通过
	private int pass=NO_PASS;
	
	
	public int getPass() {
		return pass;
	}
	public void setPass(int pass) {
		this.pass = pass;
	}
	public Integer getPublish() {
		return publish;
	}
	public void setPublish(Integer publish) {
		this.publish = publish;
	}
	public Integer getPid() {
		return pid;
	}
	public void setPid(Integer pid) {
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
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getAccepted() {
		return accepted;
	}
	public void setAccepted(Integer accepted) {
		this.accepted = accepted;
	}
	public Integer getSubmited() {
		return submited;
	}
	public void setSubmited(Integer submited) {
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
	
	public Problem(String title, String desc, String inputExample,
			String outputExample, String hint, String source,
			Integer timeLimit, Integer memaryLimit, Date createTime,
			int accepted, int submited, ProblemType type) {
		super();
		this.title = title;
		this.desc = desc;
		this.inputExample = inputExample;
		this.outputExample = outputExample;
		this.hint = hint;
		this.source = source;
		this.timeLimit = timeLimit;
		this.memaryLimit = memaryLimit;
		this.createTime = createTime;
		this.accepted = accepted;
		this.submited = submited;
		this.type = type;
	}
	@Override
	public String toString() {
		return "Problem [pid=" + pid + ", title=" + title + ", desc=" + desc + ", inputExample=" + inputExample
				+ ", outputExample=" + outputExample + ", hint=" + hint + ", source=" + source + ", timeLimit="
				+ timeLimit + ", memaryLimit=" + memaryLimit + ", createTime=" + createTime + ", accepted=" + accepted
				+ ", submited=" + submited + ", type=" + type + "]";
	}
	
}
