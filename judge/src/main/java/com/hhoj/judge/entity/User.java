package com.hhoj.judge.entity;

import java.util.Date;

/**
 * 用户实体类
 * @author zhu
 *
 */
public class User {
	//用户id
	private Integer uid;
	//用户名
	private String userName;
	//密码
	private String password;
	//邮箱
	private String email;
	//角色
	private int role;
	// 提交通过次数
	private int accepted;
	//提价次数
	private int submited;
	// 通过的题数
	private int solved;
	//注册时间
	private Date registTime;
	//最后一次登录的时间
	private Date lastLoginTime;
	//个性签名
	private String sign;
	public Integer getUid() {
		return uid;
	}
	public void setUid(Integer uid) {
		this.uid = uid;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getRole() {
		return role;
	}
	public void setRole(int role) {
		this.role = role;
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
	public int getSolved() {
		return solved;
	}
	public void setSolved(int solved) {
		this.solved = solved;
	}
	public Date getRegistTime() {
		return registTime;
	}
	public void setRegistTime(Date registTime) {
		this.registTime = registTime;
	}
	public Date getLastLoginTime() {
		return lastLoginTime;
	}
	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "User [uid=" + uid + ", userName=" + userName + ", password=" + password + ", email=" + email + ", role="
				+ role + ", accepted=" + accepted + ", submited=" + submited + ", solved=" + solved + ", registTime="
				+ registTime + ", lastLoginTime=" + lastLoginTime + ", sign=" + sign + "]";
	}
	
}
