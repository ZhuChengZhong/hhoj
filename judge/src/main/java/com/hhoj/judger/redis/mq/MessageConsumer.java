package com.hhoj.judger.redis.mq;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.hhoj.judger.core.JudgeTask;
import com.hhoj.judger.entity.Submit;
import com.zhu.lzip.Compressor;
import com.zhu.lzip.LzipCompressor;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * 用来获取消息队列中提交的判题消息
 * 
 * @author zhu
 *
 */
public class MessageConsumer {

	private static Logger logger = LoggerFactory.getLogger(JudgeTask.class);
	private JedisPool pool;
	private String messageQueue;
	private Compressor compressor=new LzipCompressor();
	public MessageConsumer(JedisPool pool, String messageQueue) {
		this.pool = pool;
		this.messageQueue = messageQueue;
	}

	public Submit take(long time) {
		// 阻塞获取消息
		Jedis jedis = null;
		Submit res = null;
		try {
			jedis = pool.getResource();
			List<String> message = jedis.blpop(messageQueue, time + "");
			if (message != null && message.size() > 1) {
				String json = message.get(1);
				//解码
				byte[] bts = compressor.decompress(json.getBytes());
				String content=new String(bts);
				res = JSON.parseObject(content, Submit.class);
			}
		} catch (JSONException e) {
			logger.error("消息格式不正确！！无法转换成Submit！！");
		} finally {
			if(jedis!=null) {
				jedis.close();
			}
		}
		return res;
	}

	public Submit take() throws InterruptedException {
		while (true) {
			Submit res = take(1);
			if (res != null) {
				return res;
			}
			if (Thread.currentThread().isInterrupted()) {
				throw new InterruptedException();
			}
		}
	}
}
