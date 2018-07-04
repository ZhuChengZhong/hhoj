package com.hhoj.judger.cache;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisCache {
	
	private static JedisPool pool=new JedisPool("localhost");
	
	public static Jedis getConnection() {
		return pool.getResource();
	}
	
	public static void set(String key,String value,int seconds) {
		Jedis jedis=getConnection();
		jedis.set(key, value);
		jedis.expire(key, seconds);
		jedis.close();
	}
	
	public static String get(String key) {
		Jedis jedis=getConnection();
		String val=jedis.get(key);
		jedis.close();
		return val;
	}
	
	public static long del(String key) {
		Jedis jedis=getConnection();
		long res=jedis.del(key);
		jedis.close();
		return res;
	}
}
