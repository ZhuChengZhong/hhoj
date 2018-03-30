package com.hhoj.judge.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hhoj.judge.constant.ConfigConstant;
import com.hhoj.judge.constant.ResultConstant;
import com.hhoj.judge.entity.Problem;
import com.hhoj.judge.entity.Submit;
import com.hhoj.judge.entity.TestPoint;
import com.hhoj.judge.exception.HandlerException;
import com.hhoj.judge.handler.Handler;
import com.hhoj.judge.handler.HandlerFactory;
import com.hhoj.judge.mapper.SubmitMapper;
import com.hhoj.judge.mapper.TestPointMapper;
//提交处理任务
public class Judger implements Runnable{
	private static Logger logger=LoggerFactory.getLogger(Judger.class);
	private int submitId;
	private SubmitMapper submitMapper;
	private TestPointMapper testPointMapper;
	public Judger(int submitId,SubmitMapper submitMapper,TestPointMapper testPointMapper){
		this.submitId=submitId;
		this.submitMapper=submitMapper;
		this.testPointMapper=testPointMapper;
	}

	public void run() {

		// 从数据库中查找提交
		Submit submit = submitMapper.findById(submitId);
		if (submit == null) {
			return;
		}
		// 获取测试点
		List<TestPoint> pointList = testPointMapper.findPointListByProblemId(submit.getProblem().getPid());
		Handler handler;
		try {
			// 根据不同的语言获取不同的处理类
			handler = HandlerFactory.getHandler(submit.getLanguage().getLanguageName());
			// 调用Handler进行处理
			handler.handlerSubmit(submit, pointList);
			// 更新提交
			submitMapper.update(submit);
		} catch (HandlerException e) {
			logger.error("处理提交失败", e);
		}

	}

}
