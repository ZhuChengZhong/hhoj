package com.hhoj.judger.docker.pool;

public interface ObjectFactory<T> {
	
	/**
	 * 创建新的对象
	 * @return
	 */
	public T create();
}
