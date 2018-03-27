package com.hhoj.judger.pool;

import java.sql.Connection;
import java.sql.SQLException;
/**
 * 数据库连接验证类
 * @author zhu
 *
 */
public class ConnectionValidator implements Validator<Connection>{

	/**
	 * 判断数据库连接是否合法
	 */
	@Override
	public boolean isValid(Connection connection) {
		try {
			if(connection==null||connection.isClosed()) {
				return true;
			}
		}catch(SQLException e) {
			return true;
		}
		return false;
	}

	/**
	 * 使数据库连接失效
	 */
	@Override
	public void invalidate(Connection connection) {
		try {
			connection.close();
		} catch (SQLException e) {
			throw new RuntimeException("链接关闭时异常");
		}
	}

}
