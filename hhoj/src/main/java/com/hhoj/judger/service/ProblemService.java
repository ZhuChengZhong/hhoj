package com.hhoj.judger.service;

import java.util.List;

import com.hhoj.judger.entity.PageBean;
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
	public List<Problem> findProblems(Problem problem,PageBean pageBean);
	
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
	/**
	 * 查找某用户已通过的题目
	 * @param uid
	 * @return
	 */
	public List<Integer> findAcceptProblemByUId(Integer uid);
	
	
	/**
	 * 检查用户uid是否通过pid题
	 * @param uid
	 * @param pid
	 * @return
	 */
	public boolean checkIsAccepted(Integer uid,Integer pid);
}
