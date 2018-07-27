package com.hhoj.judger.redis.mq;


import com.hhoj.judger.entity.JudgerNode;
import com.hhoj.judger.service.impl.MonitorServiceImpl;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

public class SubcribeThread extends Thread{

	private MonitorServiceImpl monitorService=new MonitorServiceImpl();
	
	private static final String HEART_BEAT_CHANNEL="heart-channel";
	
	private  Jedis jedis;
	
	public SubcribeThread(Jedis jedis){
		this.jedis=jedis;
	}
	
	@Override
	public void run() {
		jedis.subscribe(new JedisPubSub() {
			@Override
			public void onMessage(String channel, String message) {
				String[] msgs=message.split("!");
				if(msgs.length==3){
					JudgerNode node=new JudgerNode();
					node.setJudgerName(msgs[0]);
					node.setIp(msgs[1]);
					node.setSolved(Integer.parseInt(msgs[2]));
					monitorService.updateActiveTime(node);
				}
			}
		}, HEART_BEAT_CHANNEL);
	}
	
}
