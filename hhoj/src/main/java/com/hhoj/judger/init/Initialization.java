package com.hhoj.judger.init;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.hhoj.judger.send.SendServer;

/**
 * 容器启动初始化
 * @author zhu
 *
 */
public class Initialization implements ServletContextListener{

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ServletContext context=sce.getServletContext();
		WebApplicationContext webApplicationContext=WebApplicationContextUtils.getWebApplicationContext(sce.getServletContext());
		SendServer sendServer=(SendServer)webApplicationContext.getBean("sendServer");
		sendServer.start();
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {	}

}
