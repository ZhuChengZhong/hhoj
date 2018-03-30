package com.hhoj.judge.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.ibatis.session.SqlSession;
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
import com.hhoj.judge.util.MyBatisUtil;
//提交处理任务
public class Judger implements Runnable{
	private static Logger logger=LoggerFactory.getLogger(Judger.class);
	private int submitId;
	private SqlSession sqlSession;
	private SubmitMapper submitMapper;
	private TestPointMapper testPointMapper;
	public Judger(int submitId){
		this.submitId=submitId;
		sqlSession=MyBatisUtil.getSqlSession();
		this.submitMapper=sqlSession.getMapper(SubmitMapper.class);
		this.testPointMapper=sqlSession.getMapper(TestPointMapper.class);
	}

	public void run() {

		// 从数据库中查找提交
		Submit submit = submitMapper.findById(submitId);
		if (submit == null) {
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
			// 更新提交
			submitMapper.update(submit);
			sqlSession.commit();
		} catch (HandlerException e) {
			logger.error("判定失败: submit id："+submit.getSid(),e);
		}

	}

}
