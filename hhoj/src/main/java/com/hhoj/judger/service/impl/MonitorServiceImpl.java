package com.hhoj.judger.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.hhoj.judger.entity.JudgerNode;

import redis.clients.jedis.Jedis;
@Service("monitorService")
public class MonitorServiceImpl {
	public static Jedis jedis=new Jedis("localhost",6379);
	public static HashMap<String,JudgerNode>activeNodes=new HashMap<>();
	public List<JudgerNode> getJudgerList(){
		remove();
		Set<String>set=jedis.zrange("sortset",0,-1);
		List<String>list=new ArrayList<>(set);
		Collections.sort(list);
		List<JudgerNode>res=new ArrayList<>();
		for(int i=0;i<list.size();i++){
			JudgerNode node=activeNodes.get(list.get(i));
			if(node!=null){
				res.add(node);
			}
		}
		return res;
	}
	
	private void remove(){
		long time=System.currentTimeMillis()/1000;
		jedis.zremrangeByScore("sortset",0,time-30);
	}
	
	public void stop(String judgerName){
		jedis.publish("cmd-channel",judgerName);
		activeNodes.remove(judgerName);
	}
	
	public void updateActiveTime(JudgerNode node){
		long time=System.currentTimeMillis()/1000;
		jedis.zadd("sortset",time,node.getJudgerName());
		activeNodes.put(node.getJudgerName(), node);
		remove();
	}
	
	/**
	 * 获取还未完成任务
	 * @return
	 */
	
	public long getTask(){ 
		return jedis.llen("submit-queue");
	}
}
