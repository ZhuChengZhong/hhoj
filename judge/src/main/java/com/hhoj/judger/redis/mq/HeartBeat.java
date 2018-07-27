package com.hhoj.judger.redis.mq;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hhoj.judger.core.JudgeServer;

import redis.clients.jedis.Jedis;

public class HeartBeat extends Thread{
	
	private Logger logger=LoggerFactory.getLogger(HeartBeat.class);
	
	private static final int WAIT_TIME=5;
	
	private static final String HEART_CHANNEL_NAME="heart-channel";
	
	private Jedis jedis;
	
	private JudgeServer server;
	
	public HeartBeat(JudgeServer  server,Jedis jedis) {
		this.jedis=jedis;
		this.server=server;
	}
	@Override
	public void run() {
		logger.info("开始心跳..");
		while(!server.isStop()){
			try {
				String message=server.getServerName()+"!"
						+server.getIp()+"!"
						+server.getCompletedTaskCount();
				jedis.publish(HEART_CHANNEL_NAME,message);
				TimeUnit.SECONDS.sleep(WAIT_TIME);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		jedis.close();
		logger.info("心跳停止..");
	}
	
}
