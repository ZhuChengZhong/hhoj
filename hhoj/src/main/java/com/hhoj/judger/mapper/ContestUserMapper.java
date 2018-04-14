package com.hhoj.judger.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hhoj.judger.entity.Contest;
import com.hhoj.judger.entity.User;

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
}
