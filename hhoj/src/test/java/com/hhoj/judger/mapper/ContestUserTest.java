package com.hhoj.judger.mapper;

import org.apache.ibatis.session.SqlSession;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.hhoj.judger.entity.ContestUser;
import com.hhoj.judger.entity.Language;
import com.hhoj.judger.entity.ProblemType;
import com.hhoj.judger.util.MyBatisUtil;

public class ContestUserTest {
	private SqlSession sqlSession;
	private ContestUserMapper contestUserMapper;
	@Before
	public void setUp() throws Exception {
		sqlSession=MyBatisUtil.getSqlSession();
		contestUserMapper=sqlSession.getMapper(ContestUserMapper.class);
	}

	@After
	public void tearDown() throws Exception {
		sqlSession.commit();
	}
	@Test
	public void updateContestUserTest() {
		ContestUser contestUser=new ContestUser();
		contestUser.setUseTotalMemary(10);
		contestUser.setUseTotalTime(10);
		contestUser.setSolved(3);
		contestUserMapper.updateContestUser(5, contestUser);
	}
}
