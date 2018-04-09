package com.hhoj.judger.handler;

import com.hhoj.judger.constant.ConfigConstant;
import com.hhoj.judger.exception.HandlerException;

public final class HandlerFactory {
	private static JavaHandler javaHandler = new JavaHandler(ConfigConstant.TEST_POINT_DIR_PATH,
			ConfigConstant.SOURCE_CODE_DIR_PATH);
	private static CPlusHandler cPlusHandler = new CPlusHandler(ConfigConstant.TEST_POINT_DIR_PATH,
			ConfigConstant.SOURCE_CODE_DIR_PATH);
	public static Handler getHandler(String handlerType) throws HandlerException {
		if("Java".equals(handlerType)) {
			return javaHandler;
		}
		if("c/c++".equals(handlerType)) {
			return cPlusHandler;
		}
		throw new HandlerException();
	}
}
