package com.hhoj.judger.handler;

import com.hhoj.judger.exception.HandlerException;

public final class HandlerFactory {
	private static JavaHandler javaHandler = new JavaHandler();
	private static CPlusHandler cPlusHandler = new CPlusHandler();
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
