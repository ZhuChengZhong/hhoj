package com.hhoj.judger.boot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.hhoj.judger.constant.Config;
import com.hhoj.judger.core.JudgeServer;
import com.hhoj.judger.redis.mq.MQServer;
import com.hhoj.judger.util.DockerOperator;
import com.spotify.docker.client.exceptions.DockerCertificateException;

/**
 * 评测机启动类
 * @author Administrator
 *
 */
public class JudgeBoot {
	private static Logger logger = LoggerFactory.getLogger(JudgeBoot.class);
	
	/**
	 * 消息服务器
	 */
	private static MQServer mqServer;
	
	/**
	 * 判题服务器
	 */
	private static JudgeServer server;
	
	public static void main(String[] args) throws InterruptedException, DockerCertificateException {
		Runtime.getRuntime().addShutdownHook(new JudgerShutDownHook());
		logger.info("判题机启动中...");
		 DockerOperator.instance();
		 mqServer=new MQServer(Config.REDIS_HOST,Config.SUBMIT_QUEUE,Config.RESULT_QUEUE);
		 server=new JudgeServer(mqServer.getConsumer(), mqServer.getProducer());
		 server.start();
		 logger.info("判题机启动成功！！");
	}
	
	/**
	 * 判题机退出时的清理工作
	 * @author zhu
	 *
	 */
	private static class JudgerShutDownHook extends Thread{
		@Override
		public void run() {
			logger.info("正在退出.....");
			if(server!=null) {
				server.close();
			}
			if(mqServer!=null) {
				mqServer.close();
			}
			try {
				DockerOperator.instance().close();
			} catch (DockerCertificateException e) {
				logger.info("docker关闭异常");
			}
			logger.info("判题机已关闭！！！");
		}
	}
}
