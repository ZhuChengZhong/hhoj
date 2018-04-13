package com.hhoj.judger.controller;

import java.text.ParseException;
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
import com.hhoj.judger.entity.ContestProblem;
import com.hhoj.judger.entity.PageBean;
import com.hhoj.judger.entity.Problem;
import com.hhoj.judger.entity.Role;
import com.hhoj.judger.entity.User;
import com.hhoj.judger.service.ContestService;
import com.hhoj.judger.service.ProblemService;
import com.hhoj.judger.util.DateUtil;
import com.hhoj.judger.util.PageUtil;
import com.hhoj.judger.util.ResponseUtil;

@Controller
@RequestMapping("/manager/contest")
public class AdminContestController {

	@Autowired
	private ContestService contestService;
	
	@Autowired
	private ProblemService problemService;
	/**
	 * 获取竞赛列表
	 * @param page
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/list/{page}",method= {RequestMethod.GET})
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
	
	/**
	 * 跳转竞赛添加页面
	 * @return
	 */
	@ValidatePermission(role=Role.MANAGER)
	@RequestMapping(value="/add",method= {RequestMethod.GET})
	public ModelAndView preAddContest() {
		ModelAndView mav=new ModelAndView();
		mav.addObject("mainPage", "contest/contest-add.jsp");
		mav.setViewName("manager");
		return mav;
	}
	
	
	/**
	 * 获取要更新的竞赛信息
	 * 跳转竞赛更新页面
	 * @return
	 */
	@ValidatePermission(role=Role.MANAGER)
	@RequestMapping(value="/update/{contestId}",method= {RequestMethod.GET})
	public ModelAndView preUpdateContest(@PathVariable("contestId")Integer contestId) {
		Contest contest=contestService.findContestById(contestId);
		ModelAndView mav=new ModelAndView();
		mav.addObject("contest", contest);
		mav.addObject("mainPage", "contest/contest-add.jsp");
		mav.setViewName("manager");
		return mav;
	}
	
	
	/**
	 * 添加或更新竞赛信息
	 * @param contest
	 * @param startTime
	 * @param timeLimit
	 * @param startJoinTime
	 * @param endJoinTime
	 * @param request
	 * @param response
	 * @throws ParseException
	 */
	@ValidatePermission(role=Role.MANAGER)
	@RequestMapping(value="/save",method= {RequestMethod.POST})
	public void addProblem(Contest contest,
			@RequestParam("startTime")String startTime,
			@RequestParam("timeLimit")Integer timeLimit,
			@RequestParam("startJoinTime")String startJoinTime,
			@RequestParam("endJoinTime")String endJoinTime,
			HttpServletRequest request,HttpServletResponse response) throws ParseException {
		contest.setStartTime(DateUtil.parse(startTime, "yyyy/MM/dd hh:mm"));
		contest.setTimeLimit(timeLimit);
		contest.setStartJoinTime(DateUtil.parse(startJoinTime, "yyyy/MM/dd hh:mm"));
		contest.setEndJoinTime(DateUtil.parse(endJoinTime, "yyyy/MM/dd hh:mm"));
		contest.setJoinNumber(0);
		User currentUser=(User)request.getSession().getAttribute("currentUser");
		contest.setInitiator(currentUser);
		contest.setStatus(-1);
		int result=0;
		if(contest.getContestId()!=null) {
		   result=contestService.updateContest(contest);
		}else {
			result=contestService.addContest(contest);
		}
		JSONObject o=new JSONObject();
		if(result>0) {
			o.put("result", "success");
		}else {
			o.put("result", "error");
		}
		ResponseUtil.write(o, response);
	}
	
	
	/**
	 * 获取竞赛试题列表
	 * @param contestId
	 * @return
	 */
	@RequestMapping(value="/{contestId}/problem/list",method=RequestMethod.GET)
	public ModelAndView contestProblemList(
			@PathVariable(value = "contestId") Integer contestId) {
		ModelAndView mav=new ModelAndView();
		List<ContestProblem>list=contestService.findContestProblems(contestId);
		mav.addObject("contestProblemList", list);
		mav.addObject("contestId", contestId);
		mav.addObject("mainPage", "contest/contest-problem-list.jsp");
		mav.setViewName("manager");
		return mav;
	}
	
	
	/**
	 * 添加竞赛试题
	 * @param pid
	 * @param contestId
	 * @return
	 */
	@ValidatePermission(role=Role.MANAGER)
	@RequestMapping(value="problem/add",method= {RequestMethod.POST})
	public void addContestProblem(
			@RequestParam("pid") Integer pid,
			@RequestParam("contestId") Integer contestId,
			HttpServletResponse response) {
		
		response.setCharacterEncoding("utf-8");
		Problem problem=new Problem();
		problem.setPid(pid);
		ContestProblem contestProblem=new ContestProblem();
		contestProblem.setContestId(contestId);
		contestProblem.setProblem(problem);
		contestProblem.setAccepted(0);
		contestProblem.setSubmited(0);
		int result=contestService.addContestProblem(contestProblem);
		JSONObject o=new JSONObject();
		if(result>0) {
			o.put("success", true);
			
		}else {
			o.put("success", false);
			o.put("message", "添加失败");
		}
		ResponseUtil.write(o, response);
	}
	
	
	/**
	 * 删除竞赛
	 * 
	 * @param response
	 */
	@ValidatePermission(role=Role.MANAGER)
	@RequestMapping(value="/remove/{contestId}",method= {RequestMethod.POST})
	public void removeContest(@PathVariable("contestId")Integer contestId,
			HttpServletResponse response) {
		JSONObject o=new JSONObject();
		Integer count=contestService.removeContest(contestId);
		o.put("count", count);
		ResponseUtil.write(o, response);
	}
	
	
	/**
	 * 删除竞赛试题
	 * @param cpId
	 * @param response
	 */
	@ValidatePermission(role=Role.MANAGER)
	@RequestMapping(value="/problem/remove/{cpId}",method= {RequestMethod.POST})
	public void removeContestProblem(@PathVariable("cpId")Integer cpId,
			HttpServletResponse response) {
		JSONObject o=new JSONObject();
		Integer count=contestService.removeContestProblem(cpId);
		o.put("count", count);
		ResponseUtil.write(o, response);
	}
}
