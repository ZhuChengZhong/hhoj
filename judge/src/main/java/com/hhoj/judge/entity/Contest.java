package com.hhoj.judge.entity;

import java.util.Date;

/**
 * 竞赛类实体
 * @author zhu
 *
 */
public class Contest {
	private int contestId;
	private String title;
	private String desc;
	private Date startTime;
	private Date endTime;
	private Date startJoinTime;
	private Date endJoinTime;
	private String contestPassword;
	private User initiator;
	public int getContestId() {
		return contestId;
	}
	public void setContestId(int contestId) {
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
	
}
