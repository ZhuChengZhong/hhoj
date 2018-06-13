package com.hhoj.judger.docker.pool;

import java.util.HashMap;
import java.util.Map;

public class Main {
	public static void main(String[] args) {
		Map<String,String>volumes=new HashMap<>();
		volumes.put("/home/zhu/code", "/home/zhu/code");
		BlockingPool<String>pool=ContainerPoolFactory.newContainerPool("judger", volumes);
		for(int i=0;i<10;i++) {
			String containerId=pool.get();
			System.out.println(containerId);
			pool.release(containerId);
		}
		pool.shutdown();
	}
}
