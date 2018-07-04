package com.hhoj.judger.cache;

import com.alibaba.fastjson.JSON;
import com.hhoj.judger.entity.Problem;

public class ProblemCache {
	
	private final static String PROBLEM_KEY_PRE="problem";
	
	public static void cacheProblem(Problem problem) {
		
		String key=PROBLEM_KEY_PRE+"-"+problem.getPid();
		String value=JSON.toJSONString(problem);
		RedisCache.set(key, value, 60*60);
	}
	
	public static Problem getProblem(int pid) {
		String key=PROBLEM_KEY_PRE+"-"+pid;
		String value=RedisCache.get(key);
		Problem res=JSON.parseObject(value, Problem.class);
		return res;
	}
	
	public static long delProblem(int pid) {
		String key=PROBLEM_KEY_PRE+"-"+pid;
		return RedisCache.del(key);
	}
	public static void set(String key,String value) {
		RedisCache.set(key, value, 60*60);
	}
	public static void main(String[] args) {
		/*ProblemType type=new ProblemType();
		type.setTypeId(1);
		type.setTypeName("java");
		Problem p=new Problem("title", "desc", "input", "output", "hint", "source", 1, 1, new Date(),1,1, type);
		p.setPid(1);
		Problem res=getProblem(1);
		System.out.println(res);*/
		
		System.out.println(delProblem(1));
	}
}
