package com.hhoj.judger.redis.mq;

import com.alibaba.fastjson.JSON;
import com.hhoj.judger.entity.JudgeResult;

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
	public MessageProducer(JedisPool pool,String messageQueue) {
		this.pool=pool;
		this.messageQueue=messageQueue;
	}
	/**
	 * 发送消息
	 * @param jr
	 */
	public void sendResult(JudgeResult jr) {
		//从连接池中获取Jedis客户端
		Jedis jedis=pool.getResource();
		//将结果对象转换为Json字符串
		String json=JSON.toJSONString(jr);
		//将结果发送至对应的消息队列
		jedis.lpush(messageQueue, json);
		//将连接归还连接池
		jedis.close();
	}
}
