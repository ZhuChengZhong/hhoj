package com.hhoj.judger.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.hhoj.judger.util.HttpObjectHolder;
/**
 * 将Request,和Response绑定到当前线程
 * 用户AOP拦截时使用
 * @author zhu
 *
 */
public class SaveHttpObjectFilter implements HandlerInterceptor{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpObjectHolder.setCurrentRequest((HttpServletRequest)request);
		HttpObjectHolder.setCurrentResponse((HttpServletResponse)response);
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}

}
