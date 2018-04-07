package com.hhoj.judger.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class DateUtil {
	
	/**
	 * 将字符串转换为日期类型
	 * @param source
	 * @param pattern
	 * @return
	 * @throws ParseException
	 */
	public static Date parse(String source,String pattern) throws ParseException {
		SimpleDateFormat format=new SimpleDateFormat(pattern);
		return format.parse(source);
	}
	
	/**
	 * 将日期转换为字符串类型
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String format(Date date,String pattern) {
		SimpleDateFormat format=new SimpleDateFormat(pattern);
		return format.format(date);
	}
}
