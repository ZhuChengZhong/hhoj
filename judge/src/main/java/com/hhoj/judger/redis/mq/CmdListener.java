package com.hhoj.judger.redis.mq;

import com.hhoj.judger.core.JudgeServer;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

public class CmdListener extends Thread{
	
	private Jedis jedis;
	
	private static final String RECIVE_CMD_CHANNEL="cmd-channel";
	
	private JudgeServer server;
	public CmdListener(JudgeServer server,Jedis jedis) {
		this.jedis=jedis;
		this.server=server;
	}
	
	@Override
	public void run() {
		jedis.subscribe(new JedisPubSub() {
			@Override
			public void onMessage(String channel, String message) {
				if(server.getServerName().equals(message)){
					System.out.println("接收到停止命令");
					server.close();
					unsubscribe();
				}
			}
		}, RECIVE_CMD_CHANNEL);
		jedis.close();
	}
}
