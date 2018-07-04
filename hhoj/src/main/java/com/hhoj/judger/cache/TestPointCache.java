package com.hhoj.judger.cache;

import com.alibaba.fastjson.JSON;
import com.hhoj.judger.entity.Problem;
import com.hhoj.judger.entity.TestPoint;

public class TestPointCache {
	
	private final static String TESTPOINT_KEY_PRE="testpoint";
	
	public static void cacheTestPoint(TestPoint testPoint) {
		
		String key=TESTPOINT_KEY_PRE+"-"+testPoint.getPointId();
		String value=JSON.toJSONString(testPoint);
		RedisCache.set(key, value, 60*60);
	}
	
	public static TestPoint getTestPoint(int pointid) {
		String key=TESTPOINT_KEY_PRE+"-"+pointid;
		String value=RedisCache.get(key);
		TestPoint res=JSON.parseObject(value, TestPoint.class);
		return res;
	}
	
	public static long delTestPoint(int pointid) {
		String key=TESTPOINT_KEY_PRE+"-"+pointid;
		return RedisCache.del(key);
	}
	
	public static void main(String[] args) {
		/*TestPoint point=new TestPoint();
		point.setInput("input");
		point.setOutput("output");
		point.setPid(1);
		point.setPointId(1);
		cacheTestPoint(point);
		
		TestPoint p=getTestPoint(1);
		System.out.println(p);*/
		delTestPoint(1);
	}
}
