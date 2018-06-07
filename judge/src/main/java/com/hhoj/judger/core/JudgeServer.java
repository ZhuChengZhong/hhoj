package com.hhoj.judger.core;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.hhoj.judger.entity.Submit;
import com.hhoj.judger.redis.mq.MessageConsumer;
import com.hhoj.judger.redis.mq.MessageProducer;

public class JudgeServer extends Thread{
	private Logger logger=LoggerFactory.getLogger(JudgeServer.class);
	
	private static final Integer THREAD_POOL_COUNT=5;
	
	private MessageConsumer consumer;
	
	private MessageProducer producer;
	
	private volatile boolean running=true;
	// 线程池
	private ThreadPoolExecutor executor = new ThreadPoolExecutor(THREAD_POOL_COUNT,
			THREAD_POOL_COUNT, 1, TimeUnit.SECONDS,new ArrayBlockingQueue<>(10));

	public JudgeServer(MessageConsumer consumer,MessageProducer producer) {
		this.consumer = consumer;
		this.producer=producer;
	}
	@Override
	public void run() {
		while (!Thread.currentThread().isInterrupted()&&running) {
			try {
				//如果阻塞队列中有任务则等待
				//避免获取大量判题任务后程序
				//崩溃会造成任务丢失
				while(executor.getQueue().size()>0) {
					Thread.sleep(100);
				}
				// 获取待判题目
				logger.info("等待获取任务...");
				Submit submit=consumer.take();
				logger.info("获取待判题目 submit id:"+submit.getSubmitId());
				//创建判题任务
				JudgeTask task=new JudgeTask(submit,producer);
				//提交线程池执行
				executor.execute(task);
			} catch (InterruptedException e) {
				
				Thread.currentThread().interrupt();
				
				logger.info("判题服务器响应中断退出");
			}
		}
	}
	
	/**
	 * 关闭服务器
	 */
	public void close() {
		running=false;
		executor.shutdown();
	}
}
