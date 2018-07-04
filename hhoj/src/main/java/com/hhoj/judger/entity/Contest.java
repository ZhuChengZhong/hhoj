package com.hhoj.judger.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 竞赛类实体
 * @author zhu
 *
 */
public class Contest implements Serializable,Delayed{
	private Integer contestId;
	private String title;
	private String desc;
	private Integer joinNumber;
	private Date startTime;
	private Integer timeLimit;
	private Date startJoinTime;
	private Date endJoinTime;
	private String contestPassword;
	@JSONField(serialize=false)
	private User initiator;
	private Integer status;  //比赛状态  -1  比赛未发布  0 未开始 1 正在进行 2结束
	@JSONField(serialize=false)
	private List<ContestUser>contestUsers;
	@JSONField(serialize=false)
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((contestId == null) ? 0 : contestId.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Contest other = (Contest) obj;
		if (contestId == null) {
			if (other.contestId != null)
				return false;
		} else if (!contestId.equals(other.contestId))
			return false;
		return true;
	}
	
	
	public Contest() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "Contest [contestId=" + contestId + ", title=" + title + ", desc=" + desc + ", joinNumber=" + joinNumber
				+ ", startTime=" + startTime + ", timeLimit=" + timeLimit + ", startJoinTime=" + startJoinTime
				+ ", endJoinTime=" + endJoinTime + ", contestPassword=" + contestPassword + ", initiator=" + initiator
				+ ", status=" + status + "]";
	}
	@Override
	public int compareTo(Delayed o) {
		if(o==this) {
			return 0;
		}
		if(o instanceof Contest) {
			Contest other=(Contest)o;
			long diff=this.getStartTime().getTime()-other.getStartTime().getTime();
			if(diff>0) {
				return 1;
			}
			if(diff<0) {
				return -1;
			}
			return 0;
		}
		long d=this.getDelay(TimeUnit.NANOSECONDS)-o.getDelay(TimeUnit.NANOSECONDS);
		return d>0?1:(d<0?-1:0);
	}
	@Override
	public long getDelay(TimeUnit unit) {
		return unit.convert(this.startTime.getTime()-System.currentTimeMillis(),TimeUnit.MILLISECONDS);
	}
	
	
	
	public Contest(Integer contestId, String title, String desc, Integer joinNumber, Date startTime, Integer timeLimit,
			Date startJoinTime, Date endJoinTime, String contestPassword, Integer status) {
		super();
		this.contestId = contestId;
		this.title = title;
		this.desc = desc;
		this.joinNumber = joinNumber;
		this.startTime = startTime;
		this.timeLimit = timeLimit;
		this.startJoinTime = startJoinTime;
		this.endJoinTime = endJoinTime;
		this.contestPassword = contestPassword;
		this.status = status;
	}
	public static void main(String[] args) throws InterruptedException {
		DelayQueue<Contest> queue=new DelayQueue<>();
		Contest c1=new Contest();
		c1.setContestId(1);
		c1.setStartTime(new Date(System.currentTimeMillis()+3000));
		Contest c2=new Contest();
		c2.setContestId(2);
		c2.setStartTime(new Date(System.currentTimeMillis()+5000));
		queue.put(c1);
		queue.put(c2);
		while(!queue.isEmpty()) {
			Contest c=queue.take();
			System.out.println(c.getContestId());
		}
	}
}
