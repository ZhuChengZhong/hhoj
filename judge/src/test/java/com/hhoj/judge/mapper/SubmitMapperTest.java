package com.hhoj.judge.mapper;

import static org.junit.Assert.fail;

import org.apache.ibatis.binding.MapperProxy;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import com.hhoj.judge.util.MyBatisUtil;

public class SubmitMapperTest {
	
	@Test
	public void test() {
		fail("Not yet implemented");
	}
	@Test
	public void testCount(){
		SqlSession sqlSession=MyBatisUtil.getSqlSession();
		SubmitMapper submitMapper=sqlSession.getMapper(SubmitMapper.class);
		int count=submitMapper.count();
		System.out.println(count);
	}
}
