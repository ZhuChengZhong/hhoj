package com.hhoj.judger.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hhoj.judger.entity.Contest;
import com.hhoj.judger.entity.ContestProblem;
import com.hhoj.judger.entity.PageBean;
import com.hhoj.judger.entity.User;

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
	 * 
	 * @return
	 */
	public ContestProblem findContestProblemById(Integer cpId);
	
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
	/**
	 * 查找某个用户参加的所有比赛
	 * @param uid
	 * @return
	 */
	public List<User> findContestsByUserId(Integer uid);
	
	/**
	 * 报名比赛
	 * @param uid
	 * @param contestId
	 */
	public Integer joinContest(Integer uid,Integer contestId);
	
	/**
	 * 退出比赛
	 * @param uid
	 * @param contestId
	 * @return
	 */
	public Integer exitContest(Integer uid,Integer contestId);
	
	/**
	 * 查找某比赛是否有某用户
	 * @param uid
	 * @param contestId
	 * @return
	 */
	public boolean existUser(Integer uid,Integer contestId);
	
}
