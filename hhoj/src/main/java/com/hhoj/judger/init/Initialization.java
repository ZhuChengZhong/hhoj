package com.hhoj.judger.init;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.hhoj.judger.mq.SubmitMessageListener;
import com.hhoj.judger.mq.SubmitReceiver;
import com.hhoj.judger.mq.SubmitSender;
import com.hhoj.judger.send.SendServer;

/**
 * 容器启动初始化
 * @author zhu
 *
 */
public class Initialization implements ServletContextListener{
	
	private static Logger logger = LoggerFactory.getLogger(Initialization.class);
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ServletContext context=sce.getServletContext();
		WebApplicationContext webApplicationContext=WebApplicationContextUtils.getWebApplicationContext(sce.getServletContext());
		SubmitReceiver submitReceiver=(SubmitReceiver)webApplicationContext.getBean("submitReceiver");
		SubmitMessageListener submitMessageListener=(SubmitMessageListener)webApplicationContext.getBean("submitMessageListener");
		if(submitReceiver==null||submitMessageListener==null) {
			logger.info("未找到消息接收者或消息监听器");
			throw new NullPointerException();
		}
		submitReceiver.receiveSubmit(submitMessageListener);
		logger.info("消息接收服务器成功启动");
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {	}

}
