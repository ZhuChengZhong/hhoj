package com.hhoj.judger.init;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.hhoj.judger.entity.Contest;
import com.hhoj.judger.entity.PageBean;
import com.hhoj.judger.entity.Submit;
import com.hhoj.judger.entity.User;
import com.hhoj.judger.mq.SubmitMessageListener;
import com.hhoj.judger.mq.SubmitReceiver;
import com.hhoj.judger.service.ContestService;
import com.hhoj.judger.service.ProblemService;
import com.hhoj.judger.service.SubmitService;
import com.hhoj.judger.service.UserService;

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
		//初始化消息队列
		initMQ(webApplicationContext);
		//初始化比赛
		initContest(webApplicationContext);
		//初始化全局参数
		initAttributes(webApplicationContext,context);
	}

	/**
	 * 初始化全局参数
	 * @param webApplicationContext
	 * @param context
	 */
	private void initAttributes(WebApplicationContext webApplicationContext,ServletContext context) {
		ProblemService problemService=(ProblemService)webApplicationContext.getBean("problemService");
		int globalProblemCount=problemService.findCount(null);
		
		SubmitService submitService=(SubmitService)webApplicationContext.getBean("submitService");
		Submit submit=new Submit();
		submit.setResult("Accepted");
		int globalAcceptedCount=submitService.findCount(submit);
		UserService userService=(UserService)webApplicationContext.getBean("userService");
		int globalUserCount=userService.findCount(null);
		PageBean pageBean=new PageBean(5, 1, 0);
		List<User>globalACUsers=userService.findUsers(new User(), pageBean);
		context.setAttribute("globalProblemCount", globalProblemCount);
		context.setAttribute("globalAcceptedCount", globalAcceptedCount);
		context.setAttribute("globalUserCount", globalUserCount);
		context.setAttribute("globalACUsers", globalACUsers);
	}
	/**
	 * 初始化比赛调度任务
	 * @param webApplicationContext
	 */
	private void initContest(WebApplicationContext webApplicationContext) {
		ContestService contestService = (ContestService) webApplicationContext.getBean("contestService");
		Map<String, Object> param = new HashMap<>();
		param.put("status", "status in (0,1)");
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
	 * 初始化消息队列
	 * @param webApplicationContext
	 */
	private void initMQ(WebApplicationContext webApplicationContext ) {
		SubmitReceiver submitReceiver = (SubmitReceiver) webApplicationContext.getBean("submitReceiver");
		SubmitMessageListener submitMessageListener = (SubmitMessageListener) webApplicationContext
				.getBean("submitMessageListener");
		if (submitReceiver == null || submitMessageListener == null) {
			logger.info("未找到消息接收者或消息监听器");
			throw new NullPointerException();
		}
		submitReceiver.receiveSubmit(submitMessageListener);
		logger.info("消息接收服务器成功启动");
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
					}
					long delay=contest.getStartTime().getTime()+contest.getTimeLimit()*60*60*1000-System.currentTimeMillis();
					scheduledExecutorService.schedule(new EndContestTask(contest, contestService),delay,TimeUnit.SECONDS);
					logger.info("比赛开始：" + contest.getTitle());
						
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
