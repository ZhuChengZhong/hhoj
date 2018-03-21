package com.hhoj.judger.mapper;

import static org.junit.Assert.*;

import org.apache.ibatis.session.SqlSession;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.hhoj.judger.entity.ProblemType;
import com.hhoj.judger.util.MyBatisUtil;

public class ProblemTypeMapperTest {
	private SqlSession sqlSession;
	private ProblemTypeMapper problemTypeMapper;
	@Before
	public void setUp() throws Exception {
		sqlSession=MyBatisUtil.getSqlSession();
		problemTypeMapper=sqlSession.getMapper(ProblemTypeMapper.class);
	}

	@After
	public void tearDown() throws Exception {
		sqlSession.commit();
	}
	@Test
	public void findProblemTypeByIdTest() {
		System.out.println(problemTypeMapper.findProblemTypeById(1));
	}
	
	@Test
	public void findCountTest() {
		System.out.println(problemTypeMapper.findCount());
	}
	@Test
	public void findProblemTypesTest() {
		System.out.println(problemTypeMapper.findProblemTypes());
	}
	@Test
	public void updateProblemTypeTest() {
		ProblemType problemType=new ProblemType();
		problemType.setTypeId(1);
		problemType.setTypeName("Java2");
		System.out.println(problemTypeMapper.updateProblemType(problemType));
	}
	@Test
	public void removeProblemTypeTest() {
		
		System.out.println(problemTypeMapper.removeProblemType(2));
	}
	@Test
	public void addProblemTypeTest() {
		ProblemType problemType=new ProblemType();
		problemType.setTypeName("new");
		System.out.println(problemTypeMapper.addProblemType(problemType));
	}
}
