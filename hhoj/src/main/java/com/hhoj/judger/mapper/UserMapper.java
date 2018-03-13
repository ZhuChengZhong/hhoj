package com.hhoj.judger.mapper;

import java.util.List;

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
	public void removeUser(Integer userId);
	
	/**
	 * 查询用户列表
	 * @param user 查询条件
	 * @return
	 */
	public List<User> findUsers(User user);
	
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
	 * 查询学生个数
	 * @param user
	 * @return
	 */
	public Integer findCount(User user);
}
