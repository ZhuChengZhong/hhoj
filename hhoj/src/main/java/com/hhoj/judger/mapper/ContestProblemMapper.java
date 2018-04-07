package com.hhoj.judger.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hhoj.judger.entity.ContestProblem;

/**
 * 竞赛类 映射器
 * @author zhu
 *
 */
public interface ContestProblemMapper {
	/**
	 * 添加竞赛试题
	 * @param contestProblem
	 * @return
	 */
	public Integer addContestProblem(ContestProblem contestProblem);
	
	/**
	 * 更新竞赛试题
	 * @param contestProblem
	 * @return
	 */
	public Integer updateContestProblem(ContestProblem contestProblem);
	
	/**
	 * 删除竞赛试题
	 * @param cpId
	 * @return
	 */
	public Integer removeContestProblem(Integer cpId);
	
	/**
	 * 查找竞赛试题
	 * @param cpId
	 * @return
	 */
	public ContestProblem findContestProblemById(Integer cpId);
	
	/**
	 * 查找竞赛试题集合
	 * @param contestId
	 * @return
	 */
	public List<ContestProblem> findContestProblems(Integer contestId);
	
	/**
	 * 查找竞赛试题
	 * @param pid
	 * @param contestId
	 * @return
	 */
	public ContestProblem findContestProblemByPidAndContestId(
			@Param("pid")Integer pid,@Param("contestId")Integer contestId);
}
