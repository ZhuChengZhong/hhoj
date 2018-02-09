package com.hhoj.judge.mapper;

import org.apache.ibatis.session.SqlSession;

import com.hhoj.judge.util.MyBatisUtil;

public class SubmitMapperTest {

   public static void testCount(){
	   SqlSession sqlSession=MyBatisUtil.getSqlSession();
	   Integer count=sqlSession.selectOne("SubmitMapper.count");
	   System.out.println(count);
	   MyBatisUtil.closeSqlSession();
   }
   public static void main(String[] args) {
	  testCount();
   }
}
