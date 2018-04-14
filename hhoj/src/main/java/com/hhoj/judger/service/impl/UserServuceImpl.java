package com.hhoj.judger.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hhoj.judger.entity.ContestUser;
import com.hhoj.judger.entity.PageBean;
import com.hhoj.judger.entity.User;
import com.hhoj.judger.mapper.UserMapper;
import com.hhoj.judger.service.UserService;
import com.hhoj.judger.util.StringUtil;

@Service("userService")
public class UserServuceImpl implements UserService{
	
	@Autowired
	private UserMapper userMapper;
	public List<User> findUsers(User user,PageBean pageBean) {
		return userMapper.findUsers(user,pageBean);
	}
	
	
	@Override
	@Transactional(isolation=Isolation.READ_COMMITTED,propagation=Propagation.REQUIRED)
	public Integer removeUser(Integer uid){
		return userMapper.removeUser(uid);
	}
	
	
	@Override
	public Integer findCount(User user) {
		return userMapper.findCount(user);
	}
	
	
	
	@Override
	@Transactional(isolation=Isolation.READ_COMMITTED,propagation=Propagation.REQUIRED)
	public Integer updateUser(User user) {
		return userMapper.updateUser(user);
	}
	
	
	@Override
	@Transactional(isolation=Isolation.READ_COMMITTED,propagation=Propagation.REQUIRED)
	public boolean addUser(User user) {
		/**
		 * 通过用户名和邮件查找用户是否已经存在
		 */
		if(userMapper.findUserByUserName(user.getUserName())!=null||
				userMapper.findUserByEmail(user.getEmail())!=null) {
			return false;
		}
		int n=userMapper.addUser(user);
		if(n>0) {
			return true;
		}
		return false;
	}

	
	
	@Override
	public User findUserByUserName(String userName) {
		if(StringUtil.isEmpty(userName)) {
			return null;
		}
		return userMapper.findUserByUserName(userName);
	}

	
	@Override
	public User findUserByEmail(String email) {
		if(StringUtil.isEmpty(email)) {
			return null;
		}
		return userMapper.findUserByEmail(email);
	}


	@Override
	public User findUserByUid(Integer uid) {
		if(uid==null) {
			return null;
		}
		return userMapper.findUserById(uid);
	}





	@Override
	public Integer findUserCountByContestId(Integer contestId) {
		if(contestId!=null) {
			return userMapper.findUserCountByContestId(contestId);
		}
		return null;
	}


	@Override
	public List<ContestUser> findUsersByContestId(Integer contestId) {
		if(contestId!=null) {
			return userMapper.findUsersByContestId(contestId);
		}
		return new ArrayList<ContestUser>();
	}


	@Override
	public List<ContestUser> findContestUsers(Integer contestId, PageBean pageBean) {
		if(contestId!=null&&pageBean!=null) {
			return userMapper.findContestUsers(contestId, pageBean);
		}
		return new ArrayList<ContestUser>();
	}




}
