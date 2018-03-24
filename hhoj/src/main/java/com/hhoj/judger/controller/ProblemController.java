package com.hhoj.judger.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.hhoj.judger.entity.PageBean;
import com.hhoj.judger.entity.Problem;
import com.hhoj.judger.entity.ProblemType;
import com.hhoj.judger.entity.Submit;
import com.hhoj.judger.service.ProblemService;
import com.hhoj.judger.service.SubmitService;
import com.hhoj.judger.util.PageUtil;
import com.hhoj.judger.util.ResponseUtil;

@Controller
@RequestMapping("/problem")
public class ProblemController {
	
	@Autowired
	private ProblemService problemService;
	
	@Autowired
	private SubmitService submitService;
	
	private static Logger logger=LoggerFactory.getLogger(ProblemController.class);
	
	/**
	 * 获取测试题列表
	 * @param problem
	 * @param page  
	 * @param request
	 * @return
	 */
	@RequestMapping("/list/{page}")
	public ModelAndView list(Problem problem, @PathVariable(value = "page") Integer page, HttpServletRequest request) {
		ModelAndView mav=new ModelAndView();
		if(page==null) {
			page=1;
		}
		int count=problemService.findCount(problem);
		PageBean pageBean=new PageBean(10, page, count);
		List<Problem>list=problemService.findProblems(problem,pageBean);
		String contextPath=request.getContextPath();
		String url=contextPath+"/problem/list";
		String pagination=PageUtil.getPagination(url, pageBean);
		mav.addObject("pagination", pagination);
		mav.addObject("problemList", list);
		mav.addObject("mainPage", "foreground/problem/problem-list.jsp");
		mav.setViewName("index");
		return mav;
	}
	
	/**
	 * 根据pid获取具体的测试题信息
	 * @param pid
	 * @return
	 */
	@RequestMapping("/detail/{pid}")
	public ModelAndView findProblem(@PathVariable("pid") Integer pid) {
		ModelAndView mav=new ModelAndView();
		Problem problem=problemService.findProblemById(pid);
		mav.addObject("problem", problem);
		mav.addObject("mainPage", "foreground/problem/problem-detail.jsp");
		mav.setViewName("index");
		return mav;
	}
}
