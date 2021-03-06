package com.hhoj.judger.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hhoj.judger.entity.ContestUser;
import com.hhoj.judger.entity.PageBean;
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
	public List<User> findUsers(User user,PageBean pageBean);
	
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
	 * 通过用户名来查找用户
	 * 当传入参数不合法或未找到
	 * 时返回null
	 * @param userName 
	 * @return
	 */
	public User findUserByUserName(String userName);
	
	
	/**
	 * 通过Email来查找用户
	 * 当传入参数不合法或未找到
	 * 时返回null
	 * @return
	 */
	public User findUserByEmail(String email);
	
	
	/**
	 * 通过uid来查找用户
	 * 当传入参数不合法或未找到
	 * 时返回null
	 * @return
	 */
	public User findUserByUid(Integer uid);
	
	/**
	 * 分页查找某个比赛的用户
	 * @param contestId
	 * @return
	 */
	public List<ContestUser> findContestUsers(Integer contestId,PageBean pageBean);
	
	/**
	 * 查找某个比赛的所有用户
	 * @param contestId
	 * @return
	 */
	public List<ContestUser> findUsersByContestId(Integer contestId);
	
	/**
	 * 查找比赛用户个数
	 * @param contestId
	 * @return
	 */
	public Integer findUserCountByContestId(Integer contestId);
	
	
}
