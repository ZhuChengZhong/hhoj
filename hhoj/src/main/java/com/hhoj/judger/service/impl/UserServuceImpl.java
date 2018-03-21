package com.hhoj.judger.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hhoj.judger.entity.PageBean;
import com.hhoj.judger.entity.User;
import com.hhoj.judger.mapper.UserMapper;
import com.hhoj.judger.service.UserService;

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
		User u=userMapper.findUserByUserNameOrEmail(user);
		//当前已存在用户
		if(u!=null) {
			return false;
		}
		int n=userMapper.addUser(user);
		if(n>0) {
			return true;
		}
		return false;
		
	}


	@Override
	public User findUserByUserNameOrEmail(User user) {
		if(user.getUserName()==null && user.getEmail()==null) {
			return null;
		}
		return userMapper.findUserByUserNameOrEmail(user);
	}

}
