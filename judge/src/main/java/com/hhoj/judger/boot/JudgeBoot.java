package com.hhoj.judger.boot;

import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hhoj.judger.constant.ConfigConstant;
import com.hhoj.judger.core.JudgeServer;
import com.hhoj.judger.listener.SubmitListener;
import com.hhoj.judger.mq.SubmitMessageListener;
import com.hhoj.judger.mq.SubmitReceiver;
import com.hhoj.judger.mq.SubmitSender;

/**
 * 评测机启动类
 * @author Administrator
 *
 */
public class JudgeBoot {
	private static Logger logger = LoggerFactory.getLogger(JudgeBoot.class);
	
	public static void main(String[] args) throws InterruptedException {
		
		logger.info("启动判题机！！！");
		// 创建阻塞队列用于存放submitId
		LinkedBlockingQueue<Integer> submitIdQueue = new LinkedBlockingQueue<Integer>();
		//创建消息发送者用于发送判定过的提交给服务器
		SubmitSender submitSender=new SubmitSender();
		// 开启判题服务器
		JudgeServer judger = new JudgeServer(submitIdQueue,submitSender);
		judger.start();
		// 创建消息接收者用于从服务器中获取需要判断的提交
		SubmitReceiver receiver = new SubmitReceiver();
		receiver.receiveSubmit(new SubmitMessageListener(submitIdQueue));
		// 等待判题服务器关闭
		judger.join();
		submitSender.stop();
		receiver.close();
		logger.info("判题机已关闭！！！");
	}
}
