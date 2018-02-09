package com.hhoj.judge.util;
/**
 * 字符串处理工具类
 * @author Administrator
 *
 */
public final class StringUtil {
	
	private StringUtil(){
		
	}
	
	public static boolean isEmpty(String s){
		if("".equals(s)||s==null){
			return true;
		}
		return false;
	}
	
	
	public static boolean isNotEmpty(String s){
		return !isEmpty(s);
	}
}
