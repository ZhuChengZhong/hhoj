package com.hhoj.judger.mapper.typehandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
/**
 * 用于将POJO中的Date对象转换为数据库中的bigint
 * @author Administrator
 *
 */
public class DateToLongTypeHandler implements TypeHandler<Date>{

	public void setParameter(PreparedStatement ps, int i, Date parameter,
			JdbcType jdbcType) throws SQLException {
		long time=parameter.getTime();
		ps.setLong(i, time);
	}

	public Date getResult(ResultSet rs, String columnName) throws SQLException {
		long time=rs.getLong(columnName);
		return new Date(time);
	}

	public Date getResult(ResultSet rs, int columnIndex) throws SQLException {
		long time=rs.getLong(columnIndex);
		return new Date(time);
	}

	public Date getResult(CallableStatement cs, int columnIndex)
			throws SQLException {
		long time=cs.getLong(columnIndex);
		return new Date(time);
	}
	
}
