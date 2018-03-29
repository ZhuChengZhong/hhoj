package com.hhoj.judge.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.ibatis.io.Resources;

/**
 * 配置文件读取类
 * @author Administrator
 *
 */
public final class PropertiesUtil {
	private static Properties pro;
	/**
	 * 读取配置文件
	 */
	static{
		try {
			InputStream inputStream=Resources.getResourceAsStream("judge.properties");
			pro=new Properties();
			pro.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
	/**
	 * 获取配置参数
	 * @param key
	 * @return
	 */
	public static String getParam(String key){
		return pro.getProperty(key);
	}
	
}
