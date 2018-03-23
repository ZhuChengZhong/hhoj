package com.hhoj.judger.controller;

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
import com.hhoj.judger.entity.PageBean;
import com.hhoj.judger.entity.Problem;
import com.hhoj.judger.entity.Submit;
import com.hhoj.judger.service.SubmitService;
import com.hhoj.judger.util.PageUtil;
import com.hhoj.judger.util.ResponseUtil;
import com.hhoj.judger.util.StringUtil;

@Controller
@RequestMapping("/submit")
public class SubmitController {
	
	
	@Autowired
	private SubmitService submitService;
	
	private static Logger logger=LoggerFactory.getLogger(SubmitController.class);
	
	@RequestMapping("/list/{page}")
	public ModelAndView list(Submit submit,@RequestParam(required=false,value="pid") Integer pid,@PathVariable(value="page") Integer page,HttpServletRequest request) {
		ModelAndView mav=new ModelAndView();
		if(Objects.isNull(page)) {
			page=1;
		}
		if(Objects.nonNull(pid)) {
			Problem problem=new Problem();
			problem.setPid(pid);
			submit.setProblem(problem);
		}
		int count=submitService.findCount(submit);
		PageBean pageBean=new PageBean(10, page, count);
		List<Submit>list=submitService.findSubmits(submit,pageBean);
		mav.addObject("submitList", list);
		String contextPath=request.getContextPath();
		String url=contextPath+"/submit/list";
		String pagination=PageUtil.getPagination(url, pageBean);
		mav.addObject("pagination", pagination);
		mav.addObject("mainPage", "submit/list.jsp");
		mav.setViewName("manager");
		return mav;
	}
	
	@RequestMapping("/add")
	public ModelAndView addSubmit(Submit submit) {
		ModelAndView mav=new ModelAndView();
		Integer result=submitService.addSubmit(submit);
		logger.info("add submit :"+submit);
		mav.setViewName("redirect:list");
		return mav;
	}
	
	@RequestMapping("/remove/{sid}")
	public void removeSubmit(@PathVariable("sid")Integer sid,HttpServletResponse response){
		Integer count=submitService.removeSubmit(sid);
		logger.info("remove submit : sid "+sid);
		JSONObject result=new JSONObject();
		result.put("success", true);
		result.put("count", count);
		ResponseUtil.write(result, response);
	}
	
	
	@RequestMapping("/update")
	public ModelAndView update(Submit submit) {
		ModelAndView mav=new ModelAndView();
		int result=submitService.updateSubmit(submit);
		logger.info("update submit :"+submit);
		mav.setViewName("redirect:list");
		return mav;
	}
}
