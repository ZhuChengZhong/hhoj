package com.hhoj.judger.redis.mq;


import java.io.UnsupportedEncodingException;

import org.apache.catalina.comet.CometProcessor;

import com.zhu.compress.Compressor;
import com.zhu.compress.hfm.HFMCompressor;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * 将判题结果发送至消息队列
 * @author zhu
 *
 */
public class MessageProducer{
	private JedisPool pool;
	private String messageQueue;
	private Compressor compressor=new HFMCompressor();
	public MessageProducer(JedisPool pool,String messageQueue) {
		this.pool=pool;
		this.messageQueue=messageQueue;
	}
	/**
	 * 发送消息
	 * @param jr
	 */
	public void sendMessage(String message) {
		//从连接池中获取Jedis客户端
		Jedis jedis=pool.getResource();
		//将消息压缩编码
		byte[]cpBts=compressor.compress(message.getBytes());
		//将消息发送至对应的消息队列
		try {
			jedis.lpush(messageQueue,new String(cpBts,"iso8859-1"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		//将连接归还连接池
		jedis.close();
	}
}
