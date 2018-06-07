package com.hhoj.judger.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hhoj.judger.entity.JudgeResult;
import com.hhoj.judger.entity.Submit;
import com.hhoj.judger.handler.Handler;
import com.hhoj.judger.handler.HandlerFactory;
import com.hhoj.judger.redis.mq.MessageProducer;
//提交处理任务
public class JudgeTask implements Runnable{
	private static Logger logger=LoggerFactory.getLogger(JudgeTask.class);
	//待判题目
	private Submit submit;
	// 判题结果发送者
	private MessageProducer producer;
	
	public JudgeTask(Submit submit,MessageProducer producer){
		this.submit=submit;
		this.producer=producer;
	}

	public void run() {
		
		Handler handler;
		try {
			// 根据不同的语言获取不同的处理类
			handler = HandlerFactory.getHandler(submit.getLanguage());
			// 调用Handler进行处理
			logger.info("submit-id:"+submit.getSubmitId()+" 开始判定");
			long start=System.currentTimeMillis();
			JudgeResult jr=handler.handlerSubmit(submit);
			long cost=System.currentTimeMillis()-start;
			logger.info("submit-id:"+submit.getSubmitId()+"判定成功-花费总时长："+cost+" ms");
			logger.info("结果："+jr);
			// 将判断后的提交发送给服务器
			producer.sendResult(jr);
			logger.info("submit-id:"+submit.getSubmitId()+" 将判定结果发送给服务器");
		} catch (Exception e) {
			logger.info("submit-id:"+submit.getSubmitId()+" 判定失败！！",e);
		}
	}

}
