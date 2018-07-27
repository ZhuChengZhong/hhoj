package com.hhoj.judger.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.hhoj.judger.entity.JudgerNode;
import com.hhoj.judger.service.impl.MonitorServiceImpl;
import com.hhoj.judger.util.ResponseUtil;

@Controller
@RequestMapping("/manager/monitor")
public class MonitorController {
	
	@Resource
	private MonitorServiceImpl monitorService;
	
	@RequestMapping("/list")
	public ModelAndView list(){
		ModelAndView mav=new ModelAndView();
		List<JudgerNode>list=monitorService.getJudgerList();
		mav.addObject("judgerNodes",list);
		long count=monitorService.getTask();
		mav.addObject("taskCount",count);
		mav.addObject("mainPage", "monitor/list.jsp");
		mav.setViewName("manager");
		return mav;
	}
	
	@RequestMapping("/stop/{judgerName}")
	public void stop(@PathVariable("judgerName")String judgerName,HttpServletResponse response){
		monitorService.stop(judgerName);
		JSONObject result=new JSONObject();
		result.put("success", true);
		ResponseUtil.write(result, response);
	}
}
