package com.hhoj.judger.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import com.hhoj.judger.entity.Problem;
import com.hhoj.judger.entity.ProblemType;
import com.hhoj.judger.util.MyBatisUtil;

public class ProblemMapperTest {
	SqlSession sqlSession=MyBatisUtil.getSqlSession();
	private ProblemMapper problemMapper=sqlSession.getMapper(ProblemMapper.class);
	
	@Test
	public void testFindUserById(){
		Problem problem=problemMapper.findProblemById(1);
		System.out.println(problem);
	}
	@Test
	public void testFindCount(){
		Problem problem=new Problem();
		problem.setTitle("标题");
		int count=problemMapper.findCount(problem);
		
		System.out.println(count);
	}
	@Test
	public void testAddProblem(){
		ProblemType type=new ProblemType();
		type.setTypeId(1);
		int count=problemMapper.addProblem(new Problem("标题啊","题目描述啊","a","1","没有提示","asd",1000,1000,new Date(),0,0,type));
		sqlSession.commit();
		System.out.println(count);
	}
	@Test
	public void testupdateProblem(){
		Problem problem=new Problem();
		//User user=new User();
		//user.setPassword("zzz");
		/*user.setAccepted(1);
		user.setEmail("zzz");
		user.setUid(2);*/
		problem.setPid(1);
		problem.setTitle("标题啊222");
		problem.setDesc("描述");
		int count=problemMapper.updateProblem(problem);
		System.out.println(count);
		sqlSession.commit();
	}
	
	@Test
	public void testfindUsers(){
		Problem problem=new Problem();
		problem.setTitle("标题");
		problem.setPid(1);
		List<Problem> list=problemMapper.findProblems(problem);
		System.out.println(list);
	}
	
	
}
