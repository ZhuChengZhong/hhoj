package com.hhoj.judger.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 竞赛类实体
 * @author zhu
 *
 */
public class Contest implements Serializable{
	private Integer contestId;
	private String title;
	private String desc;
	private Integer joinNumber;
	private Date startTime;
	private Integer timeLimit;
	private Date startJoinTime;
	private Date endJoinTime;
	private String contestPassword;
	private User initiator;
	private Integer status;  //比赛状态 0 未开始 1 正在进行 2结束
	private List<ContestUser>contestUsers;
	private int userStatus;  //用户的状态 0 未报名  1已报名
	
	
	public int getUserStatus() {
		return userStatus;
	}
	public void setUserStatus(int userStatus) {
		this.userStatus = userStatus;
	}
	
	public List<ContestUser> getContestUsers() {
		return contestUsers;
	}
	public void setContestUsers(List<ContestUser> contestUsers) {
		this.contestUsers = contestUsers;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getJoinNumber() {
		return joinNumber;
	}
	public void setJoinNumber(Integer joinNumber) {
		this.joinNumber = joinNumber;
	}
	public Integer getContestId() {
		return contestId;
	}
	public void setContestId(Integer contestId) {
		this.contestId = contestId;
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
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	
	public Integer getTimeLimit() {
		return timeLimit;
	}
	public void setTimeLimit(Integer timeLimit) {
		this.timeLimit = timeLimit;
	}
	public Date getStartJoinTime() {
		return startJoinTime;
	}
	public void setStartJoinTime(Date startJoinTime) {
		this.startJoinTime = startJoinTime;
	}
	public Date getEndJoinTime() {
		return endJoinTime;
	}
	public void setEndJoinTime(Date endJoinTime) {
		this.endJoinTime = endJoinTime;
	}
	public String getContestPassword() {
		return contestPassword;
	}
	public void setContestPassword(String contestPassword) {
		this.contestPassword = contestPassword;
	}
	public User getInitiator() {
		return initiator;
	}
	public void setInitiator(User initiator) {
		this.initiator = initiator;
	}
	@Override
	public String toString() {
		return "Contest [contestId=" + contestId + ", title=" + title + ", desc=" + desc + ", joinNumber=" + joinNumber
				+ ", startTime=" + startTime + ", timeLimit=" + timeLimit + ", startJoinTime=" + startJoinTime
				+ ", endJoinTime=" + endJoinTime + ", contestPassword=" + contestPassword + ", initiator=" + initiator
				+ ", status=" + status + "]";
	}
	
	
}
