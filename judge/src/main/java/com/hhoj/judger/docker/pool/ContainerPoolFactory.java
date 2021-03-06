package com.hhoj.judger.docker.pool;

import java.util.Map;

public class ContainerPoolFactory {
	
	
	public static BlockingPool<String> newContainerPool(String imageName,Map<String,String>volumes) {
		
		ContainerFactory factory=new ContainerFactory(imageName, volumes);
		
		ContainerValidator validator=new ContainerValidator();
		
		return new ContainerBlockingPool(factory, validator);
	}
	
	
	public static BlockingPool<String> newContainerPool(int capacity,String imageName,Map<String,String>volumes) {
		
		ContainerFactory factory=new ContainerFactory(imageName, volumes);
		
		ContainerValidator validator=new ContainerValidator();
		
		return new ContainerBlockingPool(capacity,factory, validator);
	}
	
	
	public static BlockingPool<String> newLocalContainerPool() {
		
		LocalContainerFactory factory=new LocalContainerFactory();
		
		LocalContainerValidator validator=new LocalContainerValidator();
		
		return new ContainerBlockingPool(factory, validator);
	}
	
	
	public static BlockingPool<String> newLocalContainerPool(int capacity) {
		
		LocalContainerFactory factory=new LocalContainerFactory();
		
		LocalContainerValidator validator=new LocalContainerValidator();
		
		return new ContainerBlockingPool(capacity,factory, validator);
	}
}
