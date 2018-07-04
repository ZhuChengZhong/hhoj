package com.hhoj.judger.controller;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.hhoj.judger.entity.Language;
import com.hhoj.judger.entity.PageBean;
import com.hhoj.judger.entity.Problem;
import com.hhoj.judger.entity.Submit;
import com.hhoj.judger.entity.User;
import com.hhoj.judger.redis.mq.MQServer;
import com.hhoj.judger.service.LanguageService;
import com.hhoj.judger.service.ProblemService;
import com.hhoj.judger.service.SubmitService;
import com.hhoj.judger.util.PageUtil;
import com.hhoj.judger.util.ResponseUtil;

@Controller
@RequestMapping("/submit")
public class SubmitController {

	private static Logger logger = LoggerFactory.getLogger(SubmitController.class);

	@Autowired
	private SubmitService submitService;

	@Autowired
	private ProblemService problemService;
	
	@Autowired
	private LanguageService languageService;
	
	@Autowired
	private MQServer mqServer;

	/**
	 * 查找指定测试题的所有提交结果
	 * 
	 * @param submit
	 * @param pid
	 * @param page
	 * @param request
	 * @return
	 */
	@RequestMapping("/problem/{pid}/list/{page}")
	public ModelAndView list(@PathVariable(value = "pid") Integer pid, @PathVariable(value = "page") Integer page,
			HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		if (Objects.isNull(page)) {
			page = 1;
		}
		Submit submit = new Submit();
		if (Objects.nonNull(pid)) {
			Problem problem = new Problem();
			problem.setPid(pid);
			submit.setProblem(problem);
		}
		int count = submitService.findCount(submit);
		PageBean pageBean = new PageBean(10, page, count);
		List<Submit> list = submitService.findSubmits(submit, pageBean);
		mav.addObject("submitList", list);
		String contextPath = request.getContextPath();
		String url = contextPath + "/submit/problem/" + pid + "/list";
		String pagination = PageUtil.getPagination(url, pageBean);

		Problem problem = problemService.findProblemById(pid);
		mav.addObject("problem", problem);
		mav.addObject("pagination", pagination);
		mav.addObject("mainPage", "foreground/problem/problem-result.jsp");
		mav.setViewName("index");
		return mav;
	}

	/**
	 * 添加新的提交并通在判题机判题
	 * 
	 * @param submit
	 * @return
	 */

	@RequestMapping("/problem/{pid}/add")
	public void addSubmit(Submit submit, @PathVariable("pid") Integer pid,
			@RequestParam("languageId") Integer languageId, HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		JSONObject jsonObject = new JSONObject();
		/**
		 * 设置所属题目
		 */
		Problem problem = problemService.findProblemById(pid);
		submit.setProblem(problem);

		/**
		 * 设置所属编程语言
		 */
		Language language = languageService.findLanguageById(languageId);
		submit.setLanguage(language);

		/**
		 * 设置提交用户
		 */
		User user = (User) request.getSession().getAttribute("currentUser");
		if(user==null) {
			jsonObject.put("success", false);
			request.getSession().setAttribute("redirect", "/problem/detail/"+pid);
			ResponseUtil.write(jsonObject, response);
			return ;
		}
		submit.setUser(user);
		/**
		 * 设置基本初始化信息
		 */
		submit.setJudged(0);
		submit.setSubmitTime(new Date());
		submit.setUseMemary(0);
		submit.setUseTime(0);
		/**
		 * 判断是否为比赛提交
		 */
		if(submit.getContestId()==null) {
			submit.setContestId(0);
		}
		submit.setResult("");
		/**
		 * 将提交添加至数据库
		 */
		submitService.addSubmit(submit);
		/**
		 * 将提交 发送给判题机进行判定
		 */
		String message=submitService.transforToMessage(submit);
		mqServer.getProducer().sendMessage(message);
		logger.info("向判题服务器发送一个判题请求,submitId:"+submit.getSid());
		jsonObject.put("success", true);
		ResponseUtil.write(jsonObject, response);
	}


}
