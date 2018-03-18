package com.hhoj.judger.service;

import java.util.List;

import com.hhoj.judger.entity.User;

/**
 * User 涓氬姟澶勭悊绫�
 * @author Administrator
 *
 */
public interface UserService {
	
	/**
	 * 鏌ヨ鐢ㄦ埛鍒楄〃
	 * @param user 鏌ヨ鏉′欢
	 * @return
	 */
	public List<User> findUsers(User user);
	
	/**
	 * 删除用户
	 * @param uid
	 * @return
	 */
	public Integer removeUser(Integer uid);
	
	/**
	 * 查询用户个数
	 * @param user
	 * @return
	 */
	public Integer findCount(User user);
	
	/**
	 * 更新用户信息
	 * @param user
	 * @return
	 */
	public Integer updateUser(User user);
	
	/**
	 * 插入用户
	 * @param user
	 */
	public boolean addUser(User user);
	
	/**
	 * 通过用户名或者email查找用户
	 * @param user
	 * @return
	 */
	public User findUserByUserNameOrEmail(User user);
}
