package com.hhoj.judger.pool;

import java.sql.Connection;
import java.sql.SQLException;

import org.springframework.jdbc.datasource.AbstractDataSource;

public class MyPoolDataSource extends AbstractDataSource{

	@Override
	public Connection getConnection() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Connection getConnection(String username, String password) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
