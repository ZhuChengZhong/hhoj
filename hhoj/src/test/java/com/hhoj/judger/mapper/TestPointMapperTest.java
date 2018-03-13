package com.hhoj.judger.mapper;

import org.apache.ibatis.session.SqlSession;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.hhoj.judger.entity.ProblemType;
import com.hhoj.judger.entity.TestPoint;
import com.hhoj.judger.util.MyBatisUtil;

public class TestPointMapperTest {
	private SqlSession sqlSession;
	private TestPointMapper testPointMapper;
	@Before
	public void setUp() throws Exception {
		sqlSession=MyBatisUtil.getSqlSession();
		testPointMapper=sqlSession.getMapper(TestPointMapper.class);
	}

	@After
	public void tearDown() throws Exception {
		sqlSession.commit();
	}
	@Test
	public void findTestPointTypeByIdTest() {
		System.out.println(testPointMapper.findTestPointById(1));
	}
	
	@Test
	public void findCountTest() {
		System.out.println(testPointMapper.findCount(2));
	}
	@Test
	public void findTestPointsTest() {
		System.out.println(testPointMapper.findTestPointTypes(2));
	}
	@Test
	public void updateTestPointTest() {
		TestPoint testPoint=new TestPoint();
		testPoint.setInput("b");
		testPoint.setInput("b");
		testPoint.setPid(2);
		testPoint.setPointId(1);
		System.out.println(testPointMapper.updateTestPointType(testPoint));
	}
	@Test
	public void removeTestPointTest() {
		
		System.out.println(testPointMapper.removeTestPointType(2));
	}
	@Test
	public void addTestPointTest() {
		TestPoint testPoint=new TestPoint();
		testPoint.setInput("bzxv");
		testPoint.setOutput("bxzc");
		testPoint.setPid(2);
		System.out.println(testPointMapper.addTestPointType(testPoint));
	}
}
