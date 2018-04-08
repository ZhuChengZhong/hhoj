package com.hhoj.judger.mapper;

import java.util.List;

import com.hhoj.judger.entity.Contest;
import com.hhoj.judger.entity.PageBean;
import com.hhoj.judger.entity.User;

/**
 * 竞赛类 映射器
 * @author zhu
 *
 */
public interface ContestMapper {
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
	 * 查找某个用户参加的所有比赛
	 * @param uid
	 * @return
	 */
	public List<User> findContestsByUserId(Integer uid);
}
