package com.hhoj.judger.util;

import java.io.IOException;
import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;



public final class MyBatisUtil {
	//不允许实例化
	private MyBatisUtil() { }
	
   private static ThreadLocal<SqlSession> sqlSessionHolder=new ThreadLocal<SqlSession>();
   private static SqlSessionFactory sqlSessionFactory;
   static{
	try {
		Reader reader = Resources.getResourceAsReader("mybatis.xml");
		sqlSessionFactory= new SqlSessionFactoryBuilder().build(reader);
	} catch (IOException e) {
		e.printStackTrace();
	}
   }
   
   /**
    * 获取连接
    * @return
    */
   public static SqlSession getSqlSession(){
	   SqlSession sqlSession=sqlSessionHolder.get();
	   if(sqlSession==null){
		   sqlSession=sqlSessionFactory.openSession();
		   sqlSessionHolder.set(sqlSession);
	   }
	   return sqlSession;
   }
   /**
    * 关闭连接
    */
   public static void closeSqlSession(){
	   SqlSession sqlSession=sqlSessionHolder.get();
	   if(sqlSession!=null){
		   sqlSession.close();
		   sqlSessionHolder.remove();
	   }
   }
}
