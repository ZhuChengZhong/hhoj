package com.hhoj.judger.constant;

import com.hhoj.judger.util.PropertiesUtil;

public abstract class Config {
	
	/**
	 * 本机暴露给docker使用的文件目录
	 */
	public static final String WORKSPACE;
	
	/**
	 * redis 主机地址
	 */
	public static final String REDIS_HOST;
	
	/**
	 * 待判题目队列名称
	 */
	public static final String SUBMIT_QUEUE;
	
	
	/**
	 * 判题结果存放队列名称
	 */
	public static final String RESULT_QUEUE;
	
	/**
	 * 编译或运行时最长的等待时间
	 */
	public static final Integer MAX_WAIT_TIME;
	
	/**
	 * 初始化加载配置参数
	 */
	static{
		
		WORKSPACE=PropertiesUtil.getParam("workspace");
		
		REDIS_HOST=PropertiesUtil.getParam("redis.host");
		
		SUBMIT_QUEUE=PropertiesUtil.getParam("redis.submit.queue");
		
		RESULT_QUEUE=PropertiesUtil.getParam("redis.result.queue");
		
		MAX_WAIT_TIME=Integer.parseInt(PropertiesUtil.getParam("max.wait.time"));
	}
}
