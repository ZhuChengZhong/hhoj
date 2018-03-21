package com.hhoj.judger.controller;

import java.util.List;

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
import com.hhoj.judger.entity.Submit;
import com.hhoj.judger.entity.TestPoint;
import com.hhoj.judger.service.TestPointService;
import com.hhoj.judger.util.PageUtil;
import com.hhoj.judger.util.ResponseUtil;

@Controller
@RequestMapping("/point")
public class ProblemTypeController {
	
	
	@Autowired
	private TestPointService testPointService;
	
	private static Logger logger=LoggerFactory.getLogger(ProblemTypeController.class);
	
	@RequestMapping("/list/{page}")
	public ModelAndView list(@RequestParam(required=false,value="pid") Integer pid,@PathVariable(value="page") Integer page) {
		ModelAndView mav=new ModelAndView();
	
		int count=testPointService.findCount(pid);
		List<TestPoint>list=testPointService.findTestPoints(pid);
		mav.addObject("pointList", list);
		if(page==null) {
			page=1;
		}
		PageBean pageBean=new PageBean(10,page, count);
		String url="point/list";
		String pagination=PageUtil.getPagination(url, pageBean);
		mav.addObject("pagination", pagination);
		mav.addObject("mainPage", "/WEB-INF/jsp/point/list.jsp");
		mav.setViewName("index");
		return mav;
	}
	
	@RequestMapping("/add")
	public ModelAndView addTestPoint(TestPoint testPoint) {
		ModelAndView mav=new ModelAndView();
		Integer result=testPointService.addTestPoint(testPoint);
		logger.info("add testPoint :"+testPoint);
		mav.setViewName("redirect:list");
		return mav;
	}
	
	@RequestMapping("/remove/{pointId}")
	public void removeTestPoint(@PathVariable("pointId")Integer pointId,HttpServletResponse response){
		Integer count=testPointService.removeTestPoint(pointId);
		logger.info("remove testPoint : pointId "+pointId);
		JSONObject result=new JSONObject();
		result.put("success", true);
		result.put("count", count);
		ResponseUtil.write(result, response);
	}
	
	
	@RequestMapping("/update")
	public ModelAndView update(TestPoint testPoint) {
		ModelAndView mav=new ModelAndView();
		int result=testPointService.updateTestPoint(testPoint);
		logger.info("update testPoint :"+testPoint);
		mav.setViewName("redirect:list");
		return mav;
	}
}
