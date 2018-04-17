package com.hhoj.judger.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.hhoj.judger.entity.User;

/**
 * 用于拦截未登录的用户
 * @author zhu
 *
 */
public class SecurityInterceptor implements HandlerInterceptor{
	
	
	/**
	 * 如果用户未等录则跳转至登录界面
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		User user=(User)request.getSession().getAttribute("currentUser");
		String contextPath=request.getContextPath();
		if(user==null) {
			String path=request.getServletPath();
			request.getSession().setAttribute("redirect", path);
			response.sendRedirect(contextPath+"/user/login");
			return false;
		}
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
