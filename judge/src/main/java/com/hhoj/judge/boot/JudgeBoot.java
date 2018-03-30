package com.hhoj.judge.boot;

import java.util.concurrent.LinkedBlockingQueue;

import com.hhoj.judge.constant.ConfigConstant;
import com.hhoj.judge.core.JudgeServer;
import com.hhoj.judge.listener.SubmitListener;

/**
 * 评测机启动类
 * @author Administrator
 *
 */
public class JudgeBoot {
	public static void main(String[] args) throws InterruptedException {
		LinkedBlockingQueue<Integer> submitIdQueue = new LinkedBlockingQueue<Integer>();
		SubmitListener submitListener = new SubmitListener(submitIdQueue, ConfigConstant.SERVER_HOST,
				ConfigConstant.SERVER_PORT);
		submitListener.start();
		JudgeServer judger = new JudgeServer(submitIdQueue);
		 judger.start();
		judger.join();
	}
}
