package com.hhoj.judge.handler;

import com.hhoj.judge.constant.ConfigConstant;
import com.hhoj.judge.exception.HandlerException;

public final class HandlerFactory {
	private static JavaHandler javaHandler = new JavaHandler(ConfigConstant.TEST_POINT_DIR_PATH,
			ConfigConstant.SOURCE_CODE_DIR_PATH);
	private static CPlusHandler cPlusHandler = new CPlusHandler(ConfigConstant.TEST_POINT_DIR_PATH,
			ConfigConstant.SOURCE_CODE_DIR_PATH);
	public static Handler getHandler(String handlerType) throws HandlerException {
		if("java".equals(handlerType)) {
			return javaHandler;
		}
		if("c/c++".equals(handlerType)) {
			return cPlusHandler;
		}
		throw new HandlerException();
	}
}
