package com.hhoj.judger.entity;
/**
 * 评论实体类
 * @author zhu
 *
 */
public class Comment {
	//id
	private Integer commentId;
	//评论用户
	private User user;
	// 评论类型
	private int type;
	//评论内容
	private String content;
	// 被评论的试题
	private Problem problem;
	public Integer getCommentId() {
		return commentId;
	}
	public void setCommentId(Integer commentId) {
		this.commentId = commentId;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Problem getProblem() {
		return problem;
	}
	public void setProblem(Problem problem) {
		this.problem = problem;
	}
	@Override
	public String toString() {
		return "Comment [commentId=" + commentId + ", user=" + user + ", type=" + type + ", content=" + content
				+ ", problem=" + problem + "]";
	}
	
}
