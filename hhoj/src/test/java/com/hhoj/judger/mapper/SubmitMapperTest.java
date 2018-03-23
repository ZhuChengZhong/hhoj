package com.hhoj.judger.mapper;

import java.util.Date;

import org.apache.ibatis.session.SqlSession;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.hhoj.judger.entity.Language;
import com.hhoj.judger.entity.Problem;
import com.hhoj.judger.entity.Submit;
import com.hhoj.judger.entity.User;
import com.hhoj.judger.util.MyBatisUtil;

public class SubmitMapperTest {
	private SqlSession sqlSession;
	private SubmitMapper submitMapper;
	@Before
	public void setUp() throws Exception {
		sqlSession=MyBatisUtil.getSqlSession();
		submitMapper=sqlSession.getMapper(SubmitMapper.class);
	}

	@After
	public void tearDown() throws Exception {
		sqlSession.commit();
	}
	
	@Test
	public void findSubmitByIdTest() {
		Submit submit=submitMapper.findSubmitById(1);
		submit.getProblem();
		submit.getUser();
		submit.getLanguage();
		System.out.println(submit);
	}
	
	@Test
	public void findCountTest() {
		Submit submit=new Submit();
		//submit.setJudged(1);
		//submit.setResult("accepted");
		System.out.println(submitMapper.findCount(submit));
	}
	
	@Test
	public void findSubmitsTest() {
		Submit submit=new Submit();
		submit.setJudged(1);
		submit.setResult("accepted");
		System.out.println(submitMapper.findSubmits(submit,null));
	}
	
	@Test
	public void updateSubmitTest() {
		Submit submit=new Submit();
		submit.setSid(1);
		submit.setJudged(1);
		submit.setResult("accepted");
		submit.setUseMemary(1000);
		submit.setUseTime(1000);
		System.out.println(submitMapper.updateSubmit(submit));
	}
	
	@Test
	public void removeSubmitTest() {
		
		System.out.println(submitMapper.removeSubmit(2));
	}
	
	@Test
	public void addSubmitTest() {
		User user=new User();
		user.setUid(1);
		Problem problem=new Problem();
		problem.setPid(1);
		Language language=new Language();
		language.setLanguageId(1);
		Submit submit=new Submit(user, problem, "ce", 0, 0, "public static class ....", new Date(), language, 0);
		System.out.println(submitMapper.addSubmit(submit));
	}
}
