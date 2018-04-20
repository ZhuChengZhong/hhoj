package com.hhoj.judger.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.ibatis.binding.MapperProxy;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hhoj.judger.constant.ConfigConstant;
import com.hhoj.judger.constant.ResultConstant;
import com.hhoj.judger.entity.Problem;
import com.hhoj.judger.entity.Submit;
import com.hhoj.judger.entity.TestPoint;
import com.hhoj.judger.exception.HandlerException;
import com.hhoj.judger.handler.Handler;
import com.hhoj.judger.handler.HandlerFactory;
import com.hhoj.judger.mapper.SubmitMapper;
import com.hhoj.judger.mapper.TestPointMapper;
import com.hhoj.judger.mq.SubmitSender;
import com.hhoj.judger.util.MyBatisUtil;
//提交处理任务
public class Judger implements Runnable{
	private static Logger logger=LoggerFactory.getLogger(Judger.class);
	private SubmitSender submitSender;
	private int submitId;
	private SqlSession sqlSession;
	private SubmitMapper submitMapper;
	private TestPointMapper testPointMapper;
	
	public Judger(int submitId,SubmitSender submitSender){
		this.submitId=submitId;
		sqlSession=MyBatisUtil.getSqlSession();
		this.submitMapper=sqlSession.getMapper(SubmitMapper.class);
		this.testPointMapper=sqlSession.getMapper(TestPointMapper.class);
		this.submitSender=submitSender;
	}

	public void run() {
		// 从数据库中查找提交
		Submit submit = submitMapper.findById(submitId);
		if (submit == null) {
			logger.info("未从服务器查询到提交信息:"+submitId);
			return;
		}
		logger.info("从服务器查询具体的提交信息:"+submit);
		// 获取测试点
		List<TestPoint> pointList = testPointMapper.findPointListByProblemId(submit.getProblem().getPid());
		
		Handler handler;
		try {
			// 根据不同的语言获取不同的处理类
			handler = HandlerFactory.getHandler(submit.getLanguage().getLanguageName());
			// 调用Handler进行处理
			logger.info("开始判定: submit id:"+submit.getSid());
			handler.handlerSubmit(submit, pointList);
			logger.info("判定成功: submit id:"+submit.getSid());
			// 将判断后的提交发送给服务器
			logger.info("将判定结果发送给服务器: submit id:"+submit.getSid());
			submitSender.sendSubmit(submit);
		} catch (HandlerException e) {
			logger.error("判定失败: submit id："+submit.getSid(),e);
		}

	}

}
