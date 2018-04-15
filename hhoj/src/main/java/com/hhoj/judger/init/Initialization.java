package com.hhoj.judger.init;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.hhoj.judger.entity.Contest;
import com.hhoj.judger.mq.SubmitMessageListener;
import com.hhoj.judger.mq.SubmitReceiver;
import com.hhoj.judger.service.ContestService;

/**
 * 容器启动初始化
 * 
 * @author zhu
 *
 */
public class Initialization implements ServletContextListener {

	private static Logger logger = LoggerFactory.getLogger(Initialization.class);

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ServletContext context = sce.getServletContext();
		WebApplicationContext webApplicationContext = WebApplicationContextUtils
				.getWebApplicationContext(sce.getServletContext());
		SubmitReceiver submitReceiver = (SubmitReceiver) webApplicationContext.getBean("submitReceiver");
		SubmitMessageListener submitMessageListener = (SubmitMessageListener) webApplicationContext
				.getBean("submitMessageListener");
		if (submitReceiver == null || submitMessageListener == null) {
			logger.info("未找到消息接收者或消息监听器");
			throw new NullPointerException();
		}
		submitReceiver.receiveSubmit(submitMessageListener);
		logger.info("消息接收服务器成功启动");

		ContestService contestService = (ContestService) webApplicationContext.getBean("contestService");
		Map<String, Object> param = new HashMap<>();
		param.put("status", "status=0");
		// 获取还未开始的比赛
		List<Contest> notBeginContest = contestService.findContests(param);
		// 延迟队列用于存放未开始的比赛
		DelayQueue<Contest> contestDelayQueue = (DelayQueue<Contest>) webApplicationContext
				.getBean("contestDelayQueue");
		
		notBeginContest.forEach((contest) -> contestDelayQueue.add(contest));
		ScheduledExecutorService scheduledExecutorService = (ScheduledExecutorService) webApplicationContext
				.getBean("scheduledExecutorService");
		new Thread(new BeginContestTask(contestDelayQueue,contestService,scheduledExecutorService)).start();
	}

	/**
	 * 开始比赛任务
	 * 
	 * @author zhu
	 *
	 */
	class BeginContestTask implements Runnable {

		private DelayQueue<Contest> contestDelayQueue;

		private ContestService contestService;

		private ScheduledExecutorService scheduledExecutorService;

		public BeginContestTask(DelayQueue<Contest> contestDelayQueue, ContestService contestService,
				ScheduledExecutorService scheduledExecutorService) {
			this.contestDelayQueue = contestDelayQueue;
			this.contestService = contestService;
			this.scheduledExecutorService = scheduledExecutorService;
		}

		@Override
		public void run() {
			while (!Thread.currentThread().isInterrupted()) {
				try {
					Contest contest = contestDelayQueue.take();
					if (contest.getStatus() == 0) {
						Contest updateContest = new Contest();
						updateContest.setContestId(contest.getContestId());
						updateContest.setStatus(1);
						contestService.updateContest(updateContest);
						contest.setStatus(1);
						long delay=contest.getStartTime().getTime()+contest.getTimeLimit()*60*60*1000-System.currentTimeMillis();
						scheduledExecutorService.schedule(new EndContestTask(contest, contestService),delay,TimeUnit.SECONDS);
						logger.info("比赛开始：" + contest.getTitle());
						
					}
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
			}
		}

	}

	/**
	 * 结束比赛任务
	 * 
	 * @author zhu
	 *
	 */
	class EndContestTask implements Runnable {
		private Contest contest;
		private ContestService contestService;

		public EndContestTask(Contest contest, ContestService contestService) {
			this.contest = contest;
			this.contestService = contestService;
		}

		@Override
		public void run() {
			Contest updateContest = new Contest();
			updateContest.setContestId(contest.getContestId());
			updateContest.setStatus(2);
			contestService.updateContest(updateContest);
			logger.info("比赛结束：" + contest.getTitle());
		}

	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	}

}
