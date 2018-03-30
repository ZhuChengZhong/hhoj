package com.hhoj.judge.core;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.ibatis.session.SqlSession;

import com.hhoj.judge.mapper.SubmitMapper;
import com.hhoj.judge.mapper.TestPointMapper;
import com.hhoj.judge.util.MyBatisUtil;

public class JudgeServer extends Thread{
	//提交实体类映射器
	private SubmitMapper submitMapper;
	//测试点实体类映射器
	private TestPointMapper testPointMapper;
	// 存放待处理的 提交 id
	private LinkedBlockingQueue<Integer> submitIdQueue;
	// 线程池
	private ExecutorService executor = new ThreadPoolExecutor(2, 5, 1, TimeUnit.SECONDS,
			new ArrayBlockingQueue<Runnable>(10));

	public JudgeServer(LinkedBlockingQueue<Integer> submitIdQueue) {
		this.submitIdQueue = submitIdQueue;
		SqlSession sqlSession = MyBatisUtil.getSqlSession();
		submitMapper = sqlSession.getMapper(SubmitMapper.class);
		testPointMapper = sqlSession.getMapper(TestPointMapper.class);
	}

	@Override
	public void run() {
		while (!Thread.currentThread().isInterrupted()) {
			try {
				int submitId = submitIdQueue.take();
				Judger judger = new Judger(submitId, submitMapper, testPointMapper);
				executor.execute(judger);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
	}

}
