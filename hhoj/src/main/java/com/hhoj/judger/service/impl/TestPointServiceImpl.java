package com.hhoj.judger.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.hhoj.judger.cache.RedisCache;
import com.hhoj.judger.entity.TestPoint;
import com.hhoj.judger.mapper.TestPointMapper;
import com.hhoj.judger.service.TestPointService;

@Service("testPointService")
public class TestPointServiceImpl implements TestPointService{
	
	@Autowired
	private TestPointMapper testPointMapper;
	
	@Override
	public TestPoint findTestPointById(Integer pointId) {
		String key="testpoint:"+pointId;
		String json=RedisCache.get(key);
		if(json!=null) {
			return JSON.parseObject(json,TestPoint.class);
		}
		TestPoint testpoint=testPointMapper.findTestPointById(pointId);
		String value=JSON.toJSONString(testpoint);
		RedisCache.set(key, value, 60*60);
		return testpoint;
	}

	@Override
	public Integer findCount(Integer pid) {
		String key="testpoint:count";
		String json=RedisCache.get(key);
		if(json!=null) {
			return Integer.parseInt(json);
		}
		int count=testPointMapper.findCount(pid);
		RedisCache.set(key, count+"", 60*60);
		return count;
	}

	@Override
	public List<TestPoint> findTestPoints(Integer pid) {
		String key="pid:"+pid+":testpoint:list";
		String json=RedisCache.get(key);
		if(json!=null) {
			return JSON.parseArray(json, TestPoint.class);
		}
		List<TestPoint>list=testPointMapper.findTestPoints(pid);
		String value=JSON.toJSONString(list);
		RedisCache.set(key, value, 60*60);
		return list;
	}

	@Override
	public Integer updateTestPoint(TestPoint testPoint) {
		RedisCache.del("testpoint:"+testPoint.getPointId());
		RedisCache.del("pid:"+testPoint.getPid()+":testpoint:list");
		RedisCache.del("testpoint:count");
		return testPointMapper.updateTestPoint(testPoint);
	}

	@Override
	public Integer removeTestPoint(Integer pointId) {
		RedisCache.del("testpoint:"+pointId);
		TestPoint point=this.findTestPointById(pointId);
		RedisCache.del("pid:"+point.getPid()+":testpoint:list");
		RedisCache.del("testpoint:count");
		return testPointMapper.removeTestPoint(pointId);
	}

	@Override
	public Integer addTestPoint(TestPoint testPoint) {
		RedisCache.del("testpoint:"+testPoint.getPointId());
		RedisCache.del("pid:"+testPoint.getPid()+":testpoint:list");
		RedisCache.del("testpoint:count");
		return testPointMapper.addTestPoint(testPoint);
	}

}
