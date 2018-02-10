package com.hhoj.judge.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

import com.hhoj.judge.constant.ConfigConstant;
import com.hhoj.judge.entity.Submit;
import com.hhoj.judge.entity.TestPoint;
import com.hhoj.judge.handler.Handler;
import com.hhoj.judge.mapper.SubmitMapper;
import com.hhoj.judge.mapper.TestPointMapper;
//提交处理类 （消费者）
public class Judger implements Runnable{
	  // 存放待处理的 提交 id
	private LinkedBlockingQueue<Integer>submitIdQueue;
	private SubmitMapper submitMapper;
	private TestPointMapper testPointMapper;
	private Handler handler;
	public Judger(LinkedBlockingQueue<Integer>submitIdQueue,SubmitMapper submitMapper,TestPointMapper testPointMapper,Handler handler){
		this.submitIdQueue=submitIdQueue;
		this.submitMapper=submitMapper;
		this.handler=handler;
		this.testPointMapper=testPointMapper;
	}
	public void run() {
		while(true){
			try {
				int submitId=submitIdQueue.take();
				Submit submit=submitMapper.findById(submitId);
				List<TestPoint> pointList=testPointMapper.findPointListByProblemId(submit.getProblem().getPid());
				Map<String,Object> map=new HashMap<String, Object>();
				
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
