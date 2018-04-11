package com.hhoj.judger.controller;

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
import com.hhoj.judger.entity.ContestUser;
import com.hhoj.judger.entity.PageBean;
import com.hhoj.judger.entity.Problem;
import com.hhoj.judger.entity.Role;
import com.hhoj.judger.entity.User;
import com.hhoj.judger.service.ContestService;
import com.hhoj.judger.service.ProblemService;
import com.hhoj.judger.service.UserService;
import com.hhoj.judger.util.PageUtil;
import com.hhoj.judger.util.ResponseUtil;

@Controller
@RequestMapping("/contest")
public class ContestController {

	@Autowired
	private ContestService contestService;
	
	@Autowired
	private ProblemService problemService;
	
	@Autowired
	private UserService userService;
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
		User currentUser=(User)request.getSession().getAttribute("currentUser");
		//如何当前用户已登录则查询此用户每个比赛的报名状态
		if(currentUser!=null) {
			list.forEach((contest)->{
				List<ContestUser>contestUsers=contest.getContestUsers();
				for(int i=0;i<contestUsers.size();i++) {
					if(contestUsers.get(i).getUser().getUid()==currentUser.getUid()) {
						//如果包含说明此用户已经报名该比赛
						contest.setUserStatus(1);
						break;
					}
				}
			});
		}
		String contextPath=request.getContextPath();
		String url=contextPath+"/contest/list";
		String pagination=PageUtil.getPagination(url, pageBean);
		mav.addObject("pagination", pagination);
		mav.addObject("contestList", list);
		mav.addObject("count", count);
		mav.addObject("mainPage", "foreground/contest/contest-list.jsp");
		mav.setViewName("index");
		return mav;
	}
	
	/**
	 * 加入比赛
	 * @param contestId
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/join/{contestId}")
	public void joinContest(@PathVariable("contestId") Integer contestId,
			HttpServletRequest request,HttpServletResponse response) {
		ModelAndView mav=new ModelAndView();
		User user=(User)request.getSession().getAttribute("currentUser");
		boolean success=false;
		if(user!=null) {
			success=contestService.joinContest(user.getUid(), contestId)>0?true:false;
		}
		JSONObject json=new JSONObject();
		json.put("success", success);
		ResponseUtil.write(json, response);
	}
	
	/**
	 * 退出比赛
	 * @param contestId
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/exit/{contestId}")
	public void exitContest(@PathVariable("contestId") Integer contestId,
			HttpServletRequest request,HttpServletResponse response) {
		ModelAndView mav=new ModelAndView();
		User user=(User)request.getSession().getAttribute("currentUser");
		boolean success=false;
		if(user!=null) {
			success=contestService.exitContest(user.getUid(), contestId)>0?true:false;
		}
		JSONObject json=new JSONObject();
		json.put("success", success);
		ResponseUtil.write(json, response);
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
