package com.hhoj.judger.mapper;

import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.hhoj.judger.entity.User;
import com.hhoj.judger.util.MyBatisUtil;

public class UserMapperTest {
	
	private UserMapper userMapper=MyBatisUtil.getSqlSession().getMapper(UserMapper.class);
	
	@Test
	public void testFindUserById(){
		User user=userMapper.findUserById(1);
		System.out.println(user);
	}
	@Test
	public void testFindCount(){
		User user=new User();
		user.setUserName("小");
		int count=userMapper.findCount(user);
		System.out.println(count);
	}
	@Test
	public void testAddUser(){
		int count=userMapper.addUser(new User("q", "q", "q", 0, 0, 0,0, new Date(), new Date(), "zzz"));
		System.out.println(count);
	}
	@Test
	public void testupdateUser(){
		User user=new User("qsa", "qsad", "qsdf", 0, 0, 0,0, new Date(), new Date(), "zzz");
		//User user=new User();
		//user.setPassword("zzz");
		/*user.setAccepted(1);
		user.setEmail("zzz");
		user.setUid(2);*/
		
		int count=userMapper.updateUser(user);
		System.out.println(count);
	}
	
	@Test
	public void testfindUsers(){
		User user=new User();
		//user.setUid(1);
		//user.setUserName("%小%");
		//user.setRole(1);
		List<User> list=userMapper.findUsers(user);
		System.out.println(list);
	}
	
	
}
