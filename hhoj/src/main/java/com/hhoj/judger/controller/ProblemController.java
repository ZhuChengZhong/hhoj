package com.hhoj.judger.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.hhoj.judger.entity.PageBean;
import com.hhoj.judger.entity.Problem;
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
		mav.addObject("mainPage", "problem/list.jsp");
		mav.setViewName("manager");
		return mav;
	}
	
	@RequestMapping("/add")
	public ModelAndView addProblem(Problem problem) {
		ModelAndView mav=new ModelAndView();
		Integer result=problemService.addProblem(problem);
		mav.setViewName("redirect:list");
		return mav;
	}
	
	@RequestMapping("/remove/{pid}")
	public void removeUser(@PathVariable("pid")Integer pid,HttpServletResponse response){
		Integer count=problemService.removeProblem(pid);
		JSONObject result=new JSONObject();
		result.put("success", true);
		result.put("count", count);
		ResponseUtil.write(result, response);
	}
	
	@RequestMapping("/problem/submit")
	public ModelAndView submit(Submit submit) {
		ModelAndView mav=new ModelAndView();
		Integer result=submitService.addSubmit(submit);
		logger.info("new submit :"+submit);
		mav.setViewName("redirect:/submit/list/"+submit.getProblem().getPid());
		return mav;
	}
	
	@RequestMapping("/problem/update")
	public ModelAndView update(Problem problem) {
		ModelAndView mav=new ModelAndView();
		int result=problemService.updateProblem(problem);
		logger.info("update problem :"+problem);
		mav.setViewName("redirect:list");
		return mav;
	}
}
