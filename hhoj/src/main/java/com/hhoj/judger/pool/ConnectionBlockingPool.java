package com.hhoj.judger.pool;

import java.sql.Connection;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class ConnectionBlockingPool implements BlockingPool<Connection>{
	
	private BlockingQueue<Connection> blockingQueue;
	
	private Validator<Connection> validator;
	
	private ObjectFactory<Connection> factory;
	
	/**
	 * 初始化容量
	 */
	private int size;
	
	
	
	/**
	 * 链接池是否关闭
	 */
	private volatile boolean isShutdown;
	
	public ConnectionBlockingPool( int size,Validator<Connection> validator,
			ObjectFactory<Connection> factory) {
		this.blockingQueue=new ArrayBlockingQueue<>(size);
		this.validator=validator;
		this.factory=factory;
		initPool(size);
		isShutdown=false;
	}
	
	private void initPool(int size) {
		for(int i=0;i<size;i++) {
			blockingQueue.add(factory.create());
		}
	}
	
	@Override
	public void release(Connection con) {
		if(validator.isValid(con)) {
			return ;
		}
		if(isShutdown) {
			validator.invalidate(con);
			return ;
		}
		while(true) {
			try {
				blockingQueue.put(con);
				break;
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
	}

	@Override
	public void shutdown() {
		isShutdown=true;
		while(!blockingQueue.isEmpty()) {
			Connection con=blockingQueue.peek();
			validator.invalidate(con);
		}
	}
	
	@Override
	public Connection get(int time, TimeUnit timeUnit) {
		if(!isShutdown) {
			Connection con=null;
			try {
				con=blockingQueue.poll(time, timeUnit);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
			return con;
		}
		throw new RuntimeException("数据库连接池已关闭！");
	}

	@Override
	public Connection get() {
		if(!isShutdown) {
			Connection con=null;
			try {
				con=blockingQueue.take();
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
			return con;
		}
		throw new RuntimeException("数据库连接池已关闭！");
	}
	
}
