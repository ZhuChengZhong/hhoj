package com.hhoj.judger.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hhoj.judger.entity.TestPoint;
import com.hhoj.judger.mapper.TestPointMapper;
import com.hhoj.judger.service.TestPointService;

@Service("testPointService")
public class TestPointServiceImpl implements TestPointService{
	
	@Autowired
	private TestPointMapper testPointMapper;
	
	@Override
	public TestPoint findTestPointById(Integer pointId) {
		return testPointMapper.findTestPointById(pointId);
	}

	@Override
	public Integer findCount(Integer pid) {
		return testPointMapper.findCount(pid);
	}

	@Override
	public List<TestPoint> findTestPoints(Integer pid) {
		return testPointMapper.findTestPoints(pid);
	}

	@Override
	public Integer updateTestPoint(TestPoint testPoint) {
		return testPointMapper.updateTestPoint(testPoint);
	}

	@Override
	public Integer removeTestPoint(Integer pointId) {
		return testPointMapper.removeTestPoint(pointId);
	}

	@Override
	public Integer addTestPoint(TestPoint testPoint) {
		return testPointMapper.addTestPoint(testPoint);
	}

}
