package com.hhoj.judger.mq;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.pool.PooledConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hhoj.judger.util.PropertiesUtil;

/**
 * 提交发送者
 * 
 * @author zhu
 *
 */
public class SubmitSender {

	private static Logger logger = LoggerFactory.getLogger(SubmitSender.class);

	private static String user = ActiveMQConnection.DEFAULT_USER;
	private static String password = ActiveMQConnection.DEFAULT_PASSWORD;
	private static String url = PropertiesUtil.getParam("activemq.url");
	private static String subject = PropertiesUtil.getParam("activemq.tojudgequeue");

	private PooledConnectionFactory factory;
	public SubmitSender() {
		ActiveMQConnectionFactory f=new ActiveMQConnectionFactory(user, password, url);
		factory = new PooledConnectionFactory(f);
	}

	/**
	 * 发送 提交
	 * 
	 * @param submit
	 */
	public void sendSubmit(Integer submitId) {
		if (submitId == null) {
			return;
		}
		Session session = null;
		MessageProducer producer = null;
		Connection connection=null;
		try {
			connection = factory.createConnection();
			connection.start();
			session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
			Destination destination = session.createQueue(subject);
			producer = session.createProducer(destination);
			MapMessage mapMessage = session.createMapMessage();
			mapMessage.setInt("submitId", submitId);
			producer.send(mapMessage);
			session.commit();
		} catch (JMSException e) {
			logger.error("消息链接创建失败");
		} finally {
			this.close(session, producer,connection);
		}
	}

	/**
	 * 关闭资源
	 * 
	 * @param session
	 * @param producer
	 */
	
	public void stop() {
		try {
			factory.stop();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void close(Session session, MessageProducer producer,Connection connection) {
		
		if (producer != null) {
			try {
				producer.close();
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
	}
}
