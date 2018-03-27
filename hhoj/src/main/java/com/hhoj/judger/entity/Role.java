package com.hhoj.judger.entity;

/**
 * 角色
 * @author zhu
 *
 */
public interface Role {
	/**
	 * 未激活账户
	 */
	public static Integer NOT_ACTIVE=0;
	/**
	 * 普通账户
	 */
	public static final int COMMON=1;
	/**
	 * 管理员
	 */
	public static final int MANAGER=2;
	/**
	 * 系统管理员
	 */
	public static final int ROOT=3;
}
