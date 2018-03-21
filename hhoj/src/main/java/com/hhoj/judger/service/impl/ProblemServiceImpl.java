package com.hhoj.judger.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hhoj.judger.entity.PageBean;
import com.hhoj.judger.entity.Problem;
import com.hhoj.judger.mapper.ProblemMapper;
import com.hhoj.judger.service.ProblemService;

@Service("problemService")
public class ProblemServiceImpl implements ProblemService{

	@Autowired
	private ProblemMapper problemMapper;
	
	@Override
	public Problem findProblemById(Integer pId) {
		return problemMapper.findProblemById(pId);
	}

	@Override
	public List<Problem> findProblems(Problem problem,PageBean pageBean) {
		return problemMapper.findProblems(problem,pageBean);
	}

	@Override
	public Integer addProblem(Problem problem) {
		return problemMapper.addProblem(problem);
	}

	@Override
	public Integer removeProblem(Integer pId) {
		return problemMapper.removeProblem(pId);
	}

	@Override
	public Integer updateProblem(Problem problem) {
		return problemMapper.updateProblem(problem);
	}

	@Override
	public Integer findCount(Problem problem) {
		return problemMapper.findCount(problem);
	}

}
