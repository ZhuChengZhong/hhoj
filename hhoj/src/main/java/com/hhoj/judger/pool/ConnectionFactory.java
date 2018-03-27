package com.hhoj.judger.pool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 产生Connection的工厂
 * @author zhu
 *
 */
public class ConnectionFactory implements ObjectFactory<Connection>{
	
	private  String url;
	private  String userName;
	private  String driver;
	private  String password;
	
	public ConnectionFactory(String url,String userName,String driver,String password){
		this.url=url;
		this.userName=userName;
		this.driver=driver;
		this.password=password;
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("数据库驱动未找到！");
		}
	}
		
	@Override
	public Connection create() {
		Connection con=null;
		try {
			con=DriverManager.getConnection(url, userName, password);
		} catch (SQLException e) {
			throw new RuntimeException("数据库链接获取失败！");
		}
		return con;
	}

}
