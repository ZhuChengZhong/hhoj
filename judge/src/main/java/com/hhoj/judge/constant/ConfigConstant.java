package com.hhoj.judge.constant;

import com.hhoj.judge.util.PropertiesUtil;

public abstract class ConfigConstant {
	//测试点数据存储目录
	public static final String TEST_POINT_DIR_PATH;
	//用户提交源码存储目录
	public static final String SOURCE_CODE_DIR_PATH;
	//用户代码输出存储路径
	public static final String USER_OUT_DIR_PATH;
	//判题机所在操作系统
	public static final String OS;
	

	/**
	 * 初始化加载配置参数
	 */
	static{
		SOURCE_CODE_DIR_PATH=PropertiesUtil.getParam("codeDir");
		TEST_POINT_DIR_PATH=PropertiesUtil.getParam("testPointDir");
		USER_OUT_DIR_PATH=PropertiesUtil.getParam("userOutDir");
		OS=PropertiesUtil.getParam("os");
	}
}
