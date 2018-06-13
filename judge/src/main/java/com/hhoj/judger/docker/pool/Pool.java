package com.hhoj.judger.docker.pool;

public interface Pool<T> {
	
	/**
	 * 从对象池中获取对象
	 * @return
	 */
	public T get();
	
	/**
	 * 将对象放回对象池
	 */
	public void release(T t);
	
	/**
	 * 关闭对象池
	 */
	public void shutdown();
}
