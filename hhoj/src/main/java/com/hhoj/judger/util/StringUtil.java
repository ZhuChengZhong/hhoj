package com.hhoj.judger.util;

public final class StringUtil {
	public static boolean isEmpty(String s) {
		if(s==null||"".equals(s)) {
			return true;
		}
		return false;
	}
	public static boolean isNotEmpty(String s) {
		return !isNotEmpty(s);
	}
}
