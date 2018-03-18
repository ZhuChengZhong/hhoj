package com.hhoj.judger.service;

import java.util.List;

import com.hhoj.judger.entity.Problem;

public interface ProblemService {
	/**
	 * 通过id查找Problem
	 * @param pId
	 * @return
	 */
	public Problem findProblemById(Integer pId);
	
	/**
	 * 查找Problem集合 
	 * @param problem 条件，为null时表示无限制条件查找
	 * @return
	 */
	public List<Problem> findProblems(Problem problem);
	
	/**
	 * 添加Problem
	 * @param problem
	 * @return
	 */
	public Integer addProblem(Problem problem);
	
	
	/**
	 * 删除Problem
	 * @param pId
	 * @return
	 */
	public Integer removeProblem(Integer pId);
	
	/**
	 * 更新Problem
	 * @param problem
	 * @return
	 */
	public Integer updateProblem(Problem problem);
	
	/**
	 * 查找Problem 数量
	 * @param problem 条件，为null时表示无限制条件查找
	 * @return
	 */
	public Integer findCount(Problem problem);
}
