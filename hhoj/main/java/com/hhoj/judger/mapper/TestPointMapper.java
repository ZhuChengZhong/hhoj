package com.hhoj.judger.mapper;

import java.util.List;

import com.hhoj.judger.entity.ProblemType;
import com.hhoj.judger.entity.TestPoint;

/**
 * TestPoint 映射器
 * @author zhuchengzhong
 *
 */
public interface TestPointMapper {
	/**
	 * 通过id查找TestPoint
	 * @param pointId
	 * @return
	 */
	public TestPoint findTestPointById(Integer pointId);
	
	/**
	 * 获取某个 Problem的TestPoint数目
	 * @return
	 */
	public Integer findCount(Integer pid);
	
	/**
	 * 获取某个 Problem的TestPoint列表
	 * @return
	 */
	public List<TestPoint> findTestPoints(Integer pid);
	
	/**
	 * 更新TestPoint
	 * @param testPoint
	 * @return
	 */
	public Integer updateTestPoint(TestPoint testPoint);
	
	/**
	 * 删除TestPoint
	 * @param pointId
	 * @return
	 */
	public Integer removeTestPoint(Integer pointId);
	
	/**
	 * 增加TestPoint
	 * @param testPoint
	 * @return
	 */
	public Integer addTestPoint(TestPoint testPoint);
}
