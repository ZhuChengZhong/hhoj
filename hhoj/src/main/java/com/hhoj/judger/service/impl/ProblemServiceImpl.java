package com.hhoj.judger.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hhoj.judger.cache.ProblemCache;
import com.hhoj.judger.cache.RedisCache;
import com.hhoj.judger.entity.PageBean;
import com.hhoj.judger.entity.Problem;
import com.hhoj.judger.mapper.ProblemMapper;
import com.hhoj.judger.service.ProblemService;

@Service("problemService")
public class ProblemServiceImpl implements ProblemService{
	
	private static Logger logger=LoggerFactory.getLogger(ProblemServiceImpl.class);
	
	@Autowired
	private ProblemMapper problemMapper;
	
	@Override
	public Problem findProblemById(Integer pid) {
		Problem res=ProblemCache.getProblem(pid);
		if(res==null) {
			res=problemMapper.findProblemById(pid);
			ProblemCache.cacheProblem(res);
		}
		return res;
	}

	@Override
	public List<Problem> findProblems(Problem problem,PageBean pageBean) {
		return problemMapper.findProblems(problem,pageBean);
	}

	@Override
	public Integer addProblem(Problem problem) {
		String key="problem:count";
		RedisCache.del(key);
		return problemMapper.addProblem(problem);
	}

	@Override
	public Integer removeProblem(Integer pId) {
		ProblemCache.delProblem(pId);
		String key="problem:count";
		RedisCache.del(key);
		return problemMapper.removeProblem(pId);
	}

	@Override
	public Integer updateProblem(Problem problem) {
		ProblemCache.delProblem(problem.getPid());
		return problemMapper.updateProblem(problem);
	}

	@Override
	public Integer findCount(Problem problem) {
		String key="problem:count";
		String res=RedisCache.get(key);
		if(res==null) {
			int count=problemMapper.findCount(problem); 
			RedisCache.set(key, count+"", 60*60);
			return count;
		}
		return Integer.parseInt(res);
	}

	@Override
	public List<Integer> findAcceptProblemByUId(Integer uid) {
		return problemMapper.findAcceptProblemByUId(uid);
	}

	@Override
	public boolean checkIsAccepted(Integer uid, Integer pid) {
		return problemMapper.checkIsAccepted(uid, pid)>0;
	}

}
