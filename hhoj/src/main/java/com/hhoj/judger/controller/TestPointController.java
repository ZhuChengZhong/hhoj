package com.hhoj.judger.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.hhoj.judger.entity.ProblemType;
import com.hhoj.judger.entity.TestPoint;
import com.hhoj.judger.service.ProblemTypeService;
import com.hhoj.judger.util.ResponseUtil;

@Controller
@RequestMapping("/type")
public class TestPointController {
	
	
	@Autowired
	private ProblemTypeService problemTypeService;
	
	private static Logger logger=LoggerFactory.getLogger(TestPointController.class);
	
	@RequestMapping("/list")
	public ModelAndView list() {
		ModelAndView mav=new ModelAndView();
		List<ProblemType>list= problemTypeService.findProblemTypes();
		mav.addObject("problemTypeList", list);
		mav.addObject("mainPage", "/WEB-INF/jsp/type/list.jsp");
		mav.setViewName("index");
		return mav;
	}
	
	@RequestMapping("/add")
	public ModelAndView addProblemType(ProblemType problemType) {
		ModelAndView mav=new ModelAndView();
		Integer result=problemTypeService.addProblemType(problemType);
		logger.info("add problemType :"+problemType);
		mav.setViewName("redirect:list");
		return mav;
	}
	
	@RequestMapping("/remove/{typeId}")
	public void removeProblemType(@PathVariable("typeId")Integer typeId,HttpServletResponse response){
		Integer count=problemTypeService.removeProblemType(typeId);
		logger.info("remove problemType : typeId "+typeId);
		JSONObject result=new JSONObject();
		result.put("success", true);
		result.put("count", count);
		ResponseUtil.write(result, response);
	}
	
	
	@RequestMapping("/update")
	public ModelAndView updateProblemType(ProblemType problemType) {
		ModelAndView mav=new ModelAndView();
		int result=problemTypeService.updateProblemType(problemType);
		logger.info("update problemType :"+problemType);
		mav.setViewName("redirect:list");
		return mav;
	}
}
