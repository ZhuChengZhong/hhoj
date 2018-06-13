package com.hhoj.judger.docker.pool;

public interface Validator<T> {
	
	/**
	 * 验证是否有效
	 * @param t
	 * @return
	 */
	public boolean isValid(T t);
	
	/**
	 * 使对象失效
	 * @param t
	 */
	public void invalidate(T t);
}
