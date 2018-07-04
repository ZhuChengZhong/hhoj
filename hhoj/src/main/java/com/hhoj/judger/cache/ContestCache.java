package com.hhoj.judger.cache;

import java.util.Date;

import com.alibaba.fastjson.JSON;
import com.hhoj.judger.entity.Contest;

public class ContestCache {
	
	private final static String CONTEST_KEY_PRE="contest";
	
	public static void cacheContest(Contest contest) {
		
		String key=CONTEST_KEY_PRE+"-"+contest.getContestId();
		String value=JSON.toJSONString(contest);
		RedisCache.set(key, value, 60*60);
	}
	
	public static Contest getContest(int contestId) {
		String key=CONTEST_KEY_PRE+"-"+contestId;
		String value=RedisCache.get(key);
		Contest res=JSON.parseObject(value, Contest.class);
		return res;
	}
	
	public static long delContest(int contestId) {
		String key=CONTEST_KEY_PRE+"-"+contestId;
		return RedisCache.del(key);
	}
	
	public static void main(String[] args) {
		Contest contest=new Contest(1,"比赛","desc",1,new Date(),1,new Date(),new Date(),"123456",1);
		cacheContest(contest);
		Contest c=getContest(1);
		System.out.println(c);
		long res=delContest(1);
		System.out.println(res);
	}
}
