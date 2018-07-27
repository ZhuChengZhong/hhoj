package com.hhoj.judger.redis.mq;


import java.io.UnsupportedEncodingException;

import com.zhu.lzip.Compressor;
import com.zhu.lzip.LzipCompressor;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * 灏嗗垽棰樼粨鏋滃彂閫佽嚦娑堟伅闃熷垪
 * @author zhu
 *
 */
public class MessageProducer{
	private JedisPool pool;
	private String messageQueue;
	private Compressor compressor=new LzipCompressor();
	public MessageProducer(JedisPool pool,String messageQueue) {
		this.pool=pool;
		this.messageQueue=messageQueue;
	}
	/**
	 * 鍙戦�佹秷鎭�
	 * @param jr
	 */
	public void sendMessage(String message) {
		//浠庤繛鎺ユ睜涓幏鍙朖edis瀹㈡埛绔�
		Jedis jedis=pool.getResource();
		//灏嗘秷鎭帇缂╃紪鐮�
		byte[]cpBts=compressor.compress(message.getBytes());
		//灏嗘秷鎭彂閫佽嚦瀵瑰簲鐨勬秷鎭槦鍒�
		try {
			jedis.lpush(messageQueue,new String(cpBts,"iso8859-1"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		//灏嗚繛鎺ュ綊杩樿繛鎺ユ睜
		jedis.close();
	}
}
