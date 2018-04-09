package com.hhoj.judger.mapper;

import java.util.List;

import com.hhoj.judger.entity.TestPoint;

public interface TestPointMapper {
	/**
	 * 根据试题的id查询测试点集合
	 * @param pid
	 * @return
	 */
	public List<TestPoint> findPointListByProblemId(int pid);
}
