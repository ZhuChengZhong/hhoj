package com.hhoj.judger.entity;
/**
 * 参赛用户
 * @author zhu
 *
 */
public class ContestUser {
	
	/**
	 * id
	 */
	private Integer cuId;
	/**
	 * 用户
	 */
	private User user;
	/**
	 * 通过题目数
	 */
	private Integer solved;
	/**
	 * 所属竞赛id
	 */
	private Integer contestId;
	/**
	 * 使用总时间
	 */
	private Integer useTotalTime;
	/**
	 * 使用总内存
	 */
	private Integer useTotalMemary;
	
	public Integer getCuId() {
		return cuId;
	}
	public void setCuId(Integer cuId) {
		this.cuId = cuId;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Integer getSolved() {
		return solved;
	}
	public void setSolved(Integer solved) {
		this.solved = solved;
	}
	public Integer getContestId() {
		return contestId;
	}
	public void setContestId(Integer contestId) {
		this.contestId = contestId;
	}
	public Integer getUseTotalTime() {
		return useTotalTime;
	}
	public void setUseTotalTime(Integer useTotalTime) {
		this.useTotalTime = useTotalTime;
	}
	public Integer getUseTotalMemary() {
		return useTotalMemary;
	}
	public void setUseTotalMemary(Integer useTotalMemary) {
		this.useTotalMemary = useTotalMemary;
	}
	
	
}
