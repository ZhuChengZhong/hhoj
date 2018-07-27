package com.hhoj.judger.redis.mq;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hhoj.judger.core.JudgeServer;
import com.hhoj.judger.core.JudgeTask;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class MQServer {
	private static Logger logger=LoggerFactory.getLogger(JudgeTask.class);
	//redis连接池
	private JedisPool pool;
	//判题请求接收者
	private MessageConsumer consumer;
	//判题结果发送者
	private MessageProducer producer;
	
	public MQServer(String redisHost,String submitQueue,String resultQueue,JudgeServer server) {
		//实例化redis连接池
		pool=new JedisPool(redisHost);
		consumer=new MessageConsumer(pool, submitQueue);
		producer=new MessageProducer(pool, resultQueue);
		Jedis jedis=pool.getResource();
		long id=jedis.incr("JUDGER-ID");
		String name="JUDGER-"+id;
		jedis.close();
		server.setServerName(name);
		server.setConsumer(consumer);
		server.setProducer(producer);
		InetAddress address=null;
		try {
			address=Inet4Address.getLocalHost();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		server.setIp(address.getHostAddress());
		HeartBeat beat=new HeartBeat(server,pool.getResource());
		beat.start();
		CmdListener listener=new CmdListener(server,pool.getResource());
		listener.start();
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
