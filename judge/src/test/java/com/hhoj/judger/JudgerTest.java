package com.hhoj.judger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.hhoj.judger.entity.Submit;
import com.hhoj.judger.entity.TestPoint;
import com.hhoj.judger.util.FileUtil;

import redis.clients.jedis.Jedis;

public class JudgerTest {
	
	private static void createTestPoint(String fileName,int n) throws FileNotFoundException {
		File file=new File(fileName);
		PrintWriter writer=new PrintWriter(new FileOutputStream(file));
		writer.println(n);
		for(int i=1;i<=n;i++) {
			writer.println(i);
		}
		writer.close();
	}
	public static void main(String[] args) {
		//创建Redis客户端
		Jedis jedis=new Jedis("localhost");
		//获取运行代码
		String code=FileUtil.getCharsFromFile("/home/zhu/code/example.cpp");
		List<TestPoint> points=new ArrayList<>();
		//获取测试点数据
		for(int i=1;i<=4;i++) {
			String inputData1=FileUtil.getCharsFromFile("/home/zhu/code/"+i+".in");
			String outputData1=FileUtil.getCharsFromFile("/home/zhu/code/"+i+".out");
			TestPoint point=new TestPoint(inputData1, outputData1);
			points.add(point);
		}
		System.out.println("-----");
		long begin=System.currentTimeMillis();
		//发送给判题机执行
		for(int i=1;i<=1;i++) {
			Submit submit=new Submit(i, 4000, 65535, "c/c++",code, points);
			//生成Json字符串
			String json=JSON.toJSONString(submit);
			//添加至消息队列
			jedis.lpush("submit-queue", json);
			System.out.println("发送成功");
		}
		//接收结果
		int count=1;
		while(count<=1) {
			List<String> result=jedis.brpop("result-queue","1000");
			if(result!=null) {
				System.out.println(result);
				count++;
			}
		}
		long cost=System.currentTimeMillis()-begin;
		System.out.println("总花费时间为："+cost+" ms");
	}
/*
	public static void main(String[] args) throws FileNotFoundException {
		createTestPoint("/home/zhu/code/1.in", 100);
		createTestPoint("/home/zhu/code/2.in", 1000);
		createTestPoint("/home/zhu/code/3.in", 10000);
		createTestPoint("/home/zhu/code/4.in", 100000);
	}*/
}
