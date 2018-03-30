package com.hhoj.judge.mapper;

import static org.junit.Assert.fail;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import com.hhoj.judge.entity.Language;
import com.hhoj.judge.entity.Problem;
import com.hhoj.judge.entity.Submit;
import com.hhoj.judge.entity.TestPoint;
import com.hhoj.judge.util.MyBatisUtil;

public class SubmitMapperTest {
	
	@Test
	public void test() {
		fail("Not yet implemented");
	}
	@Test
	public void testSubmit(){
		SqlSession sqlSession=MyBatisUtil.getSqlSession();
		SubmitMapper submitMapper=sqlSession.getMapper(SubmitMapper.class);
		Submit submit=submitMapper.findById(2);
		System.out.println(submit);
	}
	@Test
	public void testProblem(){
		SqlSession sqlSession=MyBatisUtil.getSqlSession();
		ProblemMapper problemMapper=sqlSession.getMapper(ProblemMapper.class);
		Problem problem=problemMapper.findProblemById(10);
		System.out.println(problem);
	}
	
	@Test
	public void testLanguage(){
		SqlSession sqlSession=MyBatisUtil.getSqlSession();
		LanguageMapper languageMapper=sqlSession.getMapper(LanguageMapper.class);
		Language language=languageMapper.findLanguageById(1);
		System.out.println(language);
	}
	@Test
	public void testTestPoint(){
		SqlSession sqlSession=MyBatisUtil.getSqlSession();
		TestPointMapper testPointMapper=sqlSession.getMapper(TestPointMapper.class);
		List<TestPoint> list=testPointMapper.findPointListByProblemId(1);
		System.out.println(list);
	}
}
