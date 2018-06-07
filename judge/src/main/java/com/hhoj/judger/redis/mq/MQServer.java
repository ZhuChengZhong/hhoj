package com.hhoj.judger.redis.mq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hhoj.judger.core.JudgeTask;
import com.hhoj.judger.util.PropertiesUtil;

import redis.clients.jedis.JedisPool;

public class MQServer {
	private static Logger logger=LoggerFactory.getLogger(JudgeTask.class);
	//redis连接池
	private JedisPool pool;
	//判题请求接收者
	private MessageConsumer consumer;
	//判题结果发送者
	private MessageProducer producer;
	
	public MQServer(String redisHost,String submitQueue,String resultQueue) {
		//实例化redis连接池
		pool=new JedisPool(redisHost);
		consumer=new MessageConsumer(pool, submitQueue);
		producer=new MessageProducer(pool, resultQueue);
	}
	
	public void close() {
		//关闭连接池
		pool.close();
	}

	public MessageConsumer getConsumer() {
		return consumer;
	}

	public void setConsumer(MessageConsumer consumer) {
		this.consumer = consumer;
	}

	public MessageProducer getProducer() {
		return producer;
	}

	public void setProducer(MessageProducer producer) {
		this.producer = producer;
	}
	
	
}
