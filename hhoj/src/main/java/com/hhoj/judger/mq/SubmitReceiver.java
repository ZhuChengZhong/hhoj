package com.hhoj.judger.mq;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.pool.PooledConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hhoj.judger.entity.Submit;
import com.hhoj.judger.util.PropertiesUtil;

/**
 * 提交 接收者
 * @author zhu
 *
 */
public class SubmitReceiver {
	private static Logger logger = LoggerFactory.getLogger(SubmitReceiver.class);

	private static String user = ActiveMQConnection.DEFAULT_USER;
	private static String password = ActiveMQConnection.DEFAULT_PASSWORD;
	private static String url = PropertiesUtil.getParam("activemq.url");
	private static String subject = PropertiesUtil.getParam("activemq.towebqueue");
	private Session session = null;
	private MessageConsumer consumer = null;
	private Connection connection=null;
	private PooledConnectionFactory factory;
	public SubmitReceiver() {
		ActiveMQConnectionFactory f=new ActiveMQConnectionFactory(user, password, url);
		factory = new PooledConnectionFactory(f);
	}

	/**
	 * 发送 提交
	 * 
	 * @param submit
	 */
	public void receiveSubmit(MessageListener listener) {
		
		try {
			connection = factory.createConnection();
			connection.start();
			session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
			Destination destination = session.createQueue(subject);
			consumer = session.createConsumer(destination);
			ObjectMessage objectMessage = session.createObjectMessage();
			
			consumer.setMessageListener(listener);
		} catch (JMSException e) {
			logger.error("消息链接创建失败");
		} 
	}

	public void commit() {
		try {
			session.commit();
		} catch (JMSException e) {
			logger.error("提交异常");
		}
	}
	/**
	 * 关闭资源
	 * 
	 * @param session
	 * @param producer
	 */
	public void close() {
		if (consumer != null) {
			try {
				consumer.close();
			} catch (JMSException e) {
				logger.error("资源关闭异常");
			}
		}
		if (session != null) {
			try {
				session.close();
			} catch (JMSException e) {
				logger.error("资源关闭异常");
			}
		}
		if(connection!=null) {
			try {
				connection.close();
			} catch (JMSException e) {
				logger.error("资源关闭异常");
			}
		}
		factory.stop();
	}
}
