package com.hhoj.judger.redis.mq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.JedisPool;

public class MQServer {
	private static Logger logger=LoggerFactory.getLogger(MQServer.class);
	//redis连接池
	private JedisPool pool;
	//判题结果接收者
	private MessageConsumer consumer;
	//消息发送者
	private MessageProducer producer;
	
	public MQServer(String redisHost,String resultQueue,String submitQueue) {
		//实例化redis连接池
		pool=new JedisPool(redisHost);
		consumer=new MessageConsumer(pool, resultQueue);
		producer=new MessageProducer(pool, submitQueue);
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
