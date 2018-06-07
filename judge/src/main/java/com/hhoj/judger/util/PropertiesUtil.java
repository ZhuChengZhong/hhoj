package com.hhoj.judger.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

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
			String path=PropertiesUtil.class.getResource("/").getPath();
			File file=new File(path+"judge.properties");
			InputStream inputStream=new FileInputStream(file);
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
