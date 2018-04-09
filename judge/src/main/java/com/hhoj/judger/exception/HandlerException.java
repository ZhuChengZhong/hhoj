package com.hhoj.judger.exception;

import java.io.IOException;

/**
 * 处理提交时发生异常类
 * @author zhu
 *
 */
public class HandlerException extends Exception{
	public HandlerException() {
		super();
	}

	public HandlerException(String message) {
		super(message);
	}
}
