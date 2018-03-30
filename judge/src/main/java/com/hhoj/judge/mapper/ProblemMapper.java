package com.hhoj.judge.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hhoj.judge.entity.Problem;


/**
 * 试题类映射器
 * @author Administrator
 *
 */
public interface ProblemMapper {
	/**
	 * 通过id查找Problem
	 * @param pId
	 * @return
	 */
	public Problem findProblemById(Integer pId);
	
	
}
