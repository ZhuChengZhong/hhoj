package com.hhoj.judger.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用于保存每个线程的request 和Response
 * @author zhu
 *
 */
public final class HttpObjectHolder {
	private static ThreadLocal<HttpServletRequest>requestThreadLocal=new ThreadLocal<>();
	private static ThreadLocal<HttpServletResponse>responseThreadLocal=new ThreadLocal<>();
	public static HttpServletRequest getCurrentRequest() {
		return requestThreadLocal.get();
	}
	public static void setCurrentRequest(HttpServletRequest request) {
		requestThreadLocal.set(request);
	}
	public static HttpServletResponse getCurrentResponse() {
		return responseThreadLocal.get();
	}
	public static void setCurrentResponse(HttpServletResponse response) {
		responseThreadLocal.set(response);
	}
}
