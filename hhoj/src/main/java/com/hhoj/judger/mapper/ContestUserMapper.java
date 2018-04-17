package com.hhoj.judger.mapper;

import org.apache.ibatis.annotations.Param;

import com.hhoj.judger.entity.ContestUser;

/**
 * 比赛用户映射类
 * @author zhu
 *
 */
public interface ContestUserMapper {
	
	/**
	 * 报名比赛
	 * @param uid
	 * @param contestId
	 */
	public Integer joinContest(@Param("uid")Integer uid,@Param("contestId")Integer contestId);
	
	/**
	 * 退出比赛
	 * 
	 * @return
	 */
	public Integer exitContest(@Param("uid")Integer uid,@Param("contestId")Integer contestId);
	
	/**
	 * 查找某比赛是否有某用户
	 * @param uid
	 * @param contestId
	 * @return
	 */
	public Integer existUser(@Param("uid")Integer uid,@Param("contestId")Integer contestId);
	
	/**
	 * 更新比赛用户
	 * @param uid
	 * @param contestUser
	 * @return
	 */
	public ContestUser updateContestUser(@Param("cuId")Integer cuId,@Param("contestUser")ContestUser contestUser);
	
	/**
	 * 查找用户
	 * @param uid
	 * @param contestId
	 * @return
	 */
	public ContestUser findContestUser(@Param("uid")Integer uid,@Param("contestId")Integer contestId);
}
