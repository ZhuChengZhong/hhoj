package com.hhoj.judger.service;

import java.util.List;

import com.hhoj.judger.entity.Contest;
import com.hhoj.judger.entity.ContestProblem;
import com.hhoj.judger.entity.PageBean;

public interface ContestService {
	/**
	 * 新增比赛
	 * @param contest
	 * @return
	 */
	public Integer addContest(Contest contest);
	/**
	 * 删除
	 * @param contestId
	 * @return
	 */
	public Integer removeContest(Integer contestId);
	/**
	 * 更新比赛
	 * @param contest
	 * @return
	 */
	public Integer updateContest(Contest contest);
	/**
	 * 查找比赛
	 * @param contestId
	 * @return
	 */
	public Contest findContestById(Integer contestId);
	/**
	 * 查找比赛集合
	 * @param contestId
	 * @return
	 */
	public List<Contest> findContests(PageBean pageBean);
	/**
	 * 查找比赛总数
	 * @return
	 */
	public Integer findCount();
	
	/**
	 * 获取竞赛试题
	 * @param contestId
	 * @return
	 */
	public List<ContestProblem> findContestProblems(Integer contestId);
	
	/**
	 * 添加竞赛试题
	 * @param contestProblem
	 * @return
	 */
	public Integer addContestProblem(ContestProblem contestProblem);
	
	
	/**
	 * 查找竞赛试题
	 * @param pid
	 * @param contestId
	 * @return
	 */
	public ContestProblem findContestProblemByPidAndContestId(Integer pid,Integer contestId);
	/**
	 * 删除竞赛试题
	 * @param cpId
	 * @return
	 */
	public Integer removeContestProblem(Integer cpId);
}
