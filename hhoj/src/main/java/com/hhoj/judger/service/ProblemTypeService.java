package com.hhoj.judger.service;

import java.util.List;

import com.hhoj.judger.entity.ProblemType;

public interface ProblemTypeService {
	/**
	 * 通过id查找ProblemType
	 * @param typeId
	 * @return
	 */
	public ProblemType findProblemTypeById(Integer typeId);
	
	/**
	 * 获取ProblemType数目
	 * @return
	 */
	public Integer findCount();
	
	/**
	 * 获取ProblemType列表
	 * @return
	 */
	public List<ProblemType> findProblemTypes();
	
	/**
	 * 更新ProblemType
	 * @param problemType
	 * @return
	 */
	public Integer updateProblemType(ProblemType problemType);
	
	/**
	 * 删除ProblemType
	 * @param typeId
	 * @return
	 */
	public Integer removeProblemType(Integer typeId);
	
	/**
	 * 增加ProblemType
	 * @param problemType
	 * @return
	 */
	public Integer addProblemType(ProblemType problemType);
}
