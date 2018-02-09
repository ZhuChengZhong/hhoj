package com.hhoj.judge.core;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

import com.hhoj.judge.constant.ConfigConstant;
import com.hhoj.judge.entity.Submit;
import com.hhoj.judge.entity.TestPoint;
import com.hhoj.judge.mapper.SubmitMapper;
import com.hhoj.judge.mapper.TestPointMapper;
//提交处理类 （消费者）
public class Judger implements Runnable{
	  // 存放待处理的 提交 id
	private LinkedBlockingQueue<Integer>submitIdQueue;
	private SubmitMapper submitMapper;
	private TestPointMapper testPointMapper;
	private Preprocessor processer;
	public Judger(LinkedBlockingQueue<Integer>submitIdQueue,SubmitMapper submitMapper,TestPointMapper testPointMapper,Preprocessor preprocessor){
		this.submitIdQueue=submitIdQueue;
		this.submitMapper=submitMapper;
		this.processer=preprocessor;
		this.testPointMapper=testPointMapper;
	}
	public void run() {
		while(true){
			try {
				int submitId=submitIdQueue.take();
				Submit submit=submitMapper.findById(submitId);
				List<TestPoint> pointList=testPointMapper.findPointListByProblemId(submit.getProblem().getPid());
				boolean isReady=processer.prepare(submit,pointList,ConfigConstant.SOURCE_CODE_DIR_PATH ,ConfigConstant.TEST_DATA_DIR_PATH);
				if(isReady){
					
				}
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
