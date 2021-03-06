package com.hhoj.judger.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hhoj.judger.entity.ContestUser;
import com.hhoj.judger.entity.PageBean;
import com.hhoj.judger.entity.User;

public interface UserMapper {
	/**
	 * 插入用户
	 * @param user
	 */
	public Integer addUser(User user);
	/**
	 * 删除用户
	 * @param userId
	 */
	public Integer removeUser(Integer userId);
	
	/**
	 * 查询用户列表
	 * @param user 查询条件
	 * @return
	 */
	public List<User> findUsers(@Param("user") User user,@Param("pageBean") PageBean pageBean);
	
	/**
	 * 通过id查询用户信息
	 * @param userId
	 * @return
	 */
	public User findUserById(Integer userId);
	
	/**
	 * 更新用户信息
	 * @param user
	 * @return
	 */
	public Integer updateUser(User user);
	
	/**
	 * 查询用户个数
	 * @param user
	 * @return
	 */
	public Integer findCount(User user);
	
	/**
	 * 通过用户名查找用户
	 * @param userName 
	 * @return
	 */
	public User findUserByUserName(String userName);
	
	
	/**
	 * 通过email
	 * @param email
	 * @return
	 */
	public User findUserByEmail(String email);
	
	/**
	 * 查找某个比赛的所有用户
	 * @param contestId
	 * @return
	 */
	public List<ContestUser> findUsersByContestId(Integer contestId);
	
	/**
	 * 分页查找某个比赛的用户
	 * @param contestId
	 * @return
	 */
	public List<ContestUser> findContestUsers(@Param("contestId")Integer contestId,@Param("pageBean")PageBean pageBean);
	
	/**
	 * 查找比赛用户个数
	 * @param contestId
	 * @return
	 */
	public Integer findUserCountByContestId(Integer contestId);
}
