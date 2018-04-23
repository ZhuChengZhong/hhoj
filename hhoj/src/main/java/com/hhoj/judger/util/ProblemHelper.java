package com.hhoj.judger.util;

import java.util.List;

import com.hhoj.judger.entity.Problem;

/**
 * 问题处理帮助类
 * @author zhu
 *
 */

public final class ProblemHelper {
	
	/**
	 * 将已通过的问题pass字段设置为PASS
	 * @param problems
	 * @param passIds
	 */
	public static void checkPassProblems(List<Problem>problems,List<Integer>passIds) {
		if(problems!=null&&passIds!=null) {
			problems.forEach((problem)->{
				if(passIds.contains(problem.getPid())) {
					problem.setPass(Problem.PASS);
				}
			});
		}
	}
}
