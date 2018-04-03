package com.hhoj.judger.entity;

import java.io.Serializable;
import java.util.Date;

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
	private Date endTime;
	private Date startJoinTime;
	private Date endJoinTime;
	private String contestPassword;
	private User initiator;
	
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
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
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
		return "Contest [contestId=" + contestId + ", title=" + title + ", desc=" + desc + ", startTime=" + startTime
				+ ", endTime=" + endTime + ", startJoinTime=" + startJoinTime + ", endJoinTime=" + endJoinTime
				+ ", contestPassword=" + contestPassword + ", initiator=" + initiator + "]";
	}
	
}
