package com.hhoj.judger.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hhoj.judger.util.HttpObjectHolder;

/**
 * 将Request绑定到当前线程
 * 用户AOP拦截时使用
 * @author zhu
 *
 */
public class SaveHttpObjectFIlter implements Filter{

	@Override
	public void init(FilterConfig filterConfig) throws ServletException { }

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpObjectHolder.setCurrentRequest((HttpServletRequest)request);
		HttpObjectHolder.setCurrentResponse((HttpServletResponse)response);
	}

	@Override
	public void destroy() { }

}
