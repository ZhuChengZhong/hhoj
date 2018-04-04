package com.hhoj.judger.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.hhoj.judger.annotation.ValidatePermission;
import com.hhoj.judger.entity.Contest;
import com.hhoj.judger.entity.PageBean;
import com.hhoj.judger.entity.ProblemType;
import com.hhoj.judger.entity.Role;
import com.hhoj.judger.service.ContestService;
import com.hhoj.judger.util.PageUtil;
import com.hhoj.judger.util.ResponseUtil;

@Controller
@RequestMapping("/manager/contest")
public class AdminContestController {

	@Autowired
	private ContestService contestService;
	
	/**
	 * 获取竞赛列表
	 * @param page
	 * @param request
	 * @return
	 */
	@RequestMapping("/list/{page}")
	public ModelAndView contestList(@PathVariable(value = "page") Integer page, HttpServletRequest request) {
		ModelAndView mav=new ModelAndView();
		if(page==null) {
			page=1;
		}
		int count=contestService.findCount();
		PageBean pageBean=new PageBean(10, page, count);
		List<Contest>list=contestService.findContests(pageBean);
		String contextPath=request.getContextPath();
		String url=contextPath+"/manager/contest/list";
		String pagination=PageUtil.getPagination(url, pageBean);
		mav.addObject("pagination", pagination);
		mav.addObject("contestList", list);
		mav.addObject("mainPage", "contest/contest-list.jsp");
		mav.setViewName("manager");
		return mav;
	}
	
	@ValidatePermission(role=Role.MANAGER)
	@RequestMapping(value="/add",method= {RequestMethod.GET})
	public ModelAndView preAddContest() {
		ModelAndView mav=new ModelAndView();
		mav.addObject("mainPage", "contest/contest-add.jsp");
		mav.setViewName("manager");
		return mav;
	}
	
	@ValidatePermission(role=Role.MANAGER)
	@RequestMapping(value="/save",method= {RequestMethod.POST})
	public void addProblem(Contest contest,
			@RequestParam("startTime")String startTime,
			@RequestParam("endTime")String endTime,
			@RequestParam("startJoinTime")String startJoinTime,
			@RequestParam("endJoinTime")String endJoinTime,HttpServletResponse response) {
		Date date=new Date();
		
		Integer result=null;
		/*if(problem.getPid()!=null) {
			result=problemService.updateProblem(problem);
		}else {
			result=problemService.addProblem(problem);
		}*/
		JSONObject o=new JSONObject();
		if(result>0) {
			o.put("result", "success");
		}else {
			o.put("result", "error");
		}
		ResponseUtil.write(o, response);
	}
}
