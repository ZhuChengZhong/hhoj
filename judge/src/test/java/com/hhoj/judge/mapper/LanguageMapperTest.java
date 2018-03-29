package com.hhoj.judge.mapper;

import org.apache.ibatis.session.SqlSession;

import com.hhoj.judge.entity.Language;
import com.hhoj.judge.util.MyBatisUtil;

public class LanguageMapperTest {

   public static void testInsert(){
	   SqlSession sqlSession=MyBatisUtil.getSqlSession();
	   Language language=new Language();
	   language.setLanguageName("c++");
	   Integer count=sqlSession.insert("LanguageMapper.insert",language);
	   System.out.println(count);
	   sqlSession.commit();
	   System.out.println(language.getLanguageId());
	   MyBatisUtil.closeSqlSession();
   }
   public static void main(String[] args) {
	   testInsert();
   }
}
