package com.hhoj.judger.pool;

import java.util.concurrent.TimeUnit;

public interface BlockingPool<T> extends Pool<T>{
	
	/**
	 * 超时获取
	 * @param time
	 * @param timeUnit
	 * @return
	 */
	public T get(int time,TimeUnit timeUnit);
	
	/**
	 * 阻塞获取
	 */
	public T get();
}
