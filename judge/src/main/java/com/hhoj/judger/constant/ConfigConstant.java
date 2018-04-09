package com.hhoj.judger.constant;

import com.hhoj.judger.util.PropertiesUtil;

public abstract class ConfigConstant {
	
	// 测试点数据存储目录
	public static final String TEST_POINT_DIR_PATH;
	// 用户提交源码存储目录
	public static final String SOURCE_CODE_DIR_PATH;
	// 用户代码输出存储路径
	public static final String USER_OUT_DIR_PATH;
	// 判题机所在操作系统
	public static final String OS;

	// 服务器端口
	public static final int SERVER_PORT;
	// 服务器主机名
	public static final String SERVER_HOST;

	/**
	 * 初始化加载配置参数
	 */
	static{
		SOURCE_CODE_DIR_PATH=PropertiesUtil.getParam("codeDir");
		TEST_POINT_DIR_PATH=PropertiesUtil.getParam("testPointDir");
		USER_OUT_DIR_PATH=PropertiesUtil.getParam("userOutDir");
		OS=PropertiesUtil.getParam("os");
		String port=PropertiesUtil.getParam("server.port");
		SERVER_PORT=Integer.parseInt(port);
		SERVER_HOST=PropertiesUtil.getParam("SERVER.HOST");
	}
}
