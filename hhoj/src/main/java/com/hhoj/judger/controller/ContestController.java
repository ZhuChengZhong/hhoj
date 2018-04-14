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
import com.hhoj.judger.entity.Submit;
import com.hhoj.judger.entity.User;
import com.hhoj.judger.service.ContestService;
import com.hhoj.judger.service.ProblemService;
import com.hhoj.judger.service.SubmitService;
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
	
	@Autowired
	private SubmitService submitService;
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
					if(contestUsers.get(i).getUser().getUid().intValue()==currentUser.getUid().intValue()) {
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
	 * 获取比赛详情
	 * @param contestId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/{contestId}", method = { RequestMethod.GET })
	public ModelAndView contestDetail(@PathVariable("contestId") Integer contestId, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		Contest contest = contestService.findContestById(contestId);
		User currentUser=(User)request.getSession().getAttribute("currentUser");
		if(currentUser!=null) {
			List<ContestUser>list=contest.getContestUsers();
			//检查该用户是否已报名该比赛
			for(ContestUser contestUser:list) {
				if(contestUser.getUser().getUid().equals(currentUser.getUid())) {
					contest.setUserStatus(1);
					break;
				}
			}
		}
		mav.addObject("contest", contest);
		mav.setViewName("foreground/contest/contest-info");
		return mav;
	}
	
	
	/**
	 * 
	 * 获取比赛所需的题目信息
	 * @param contestId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/{contestId}/problem/list", method = { RequestMethod.GET })
	public ModelAndView beginContest(@PathVariable("contestId") Integer contestId, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		Contest contest = contestService.findContestById(contestId);
		User currentUser=(User)request.getSession().getAttribute("currentUser");
		if(currentUser!=null) {
			List<ContestUser>list=contest.getContestUsers();
			//检查该用户是否已报名该比赛
			for(ContestUser contestUser:list) {
				if(contestUser.getUser().getUid().equals(currentUser.getUid())) {
					contest.setUserStatus(1);
					break;
				}
			}
		}
		request.getSession().setAttribute("myCurrentContest", contest);
		//获取比赛题目
		List<ContestProblem>contestProblemList=contestService.findContestProblems(contestId);
		mav.addObject("contestProblemList", contestProblemList);
		mav.addObject("mainPage", "contest-problem-list.jsp");
		mav.setViewName("foreground/contest/contest-detail");
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
	 * 获取比赛试题
	 * @param cpId
	 * @return
	 */
	@RequestMapping(value="/problem/{cpId}",method=RequestMethod.GET)
	public ModelAndView getContestProblem(@PathVariable("cpId") Integer cpId) {
		ModelAndView mav=new ModelAndView();
		ContestProblem contestProblem=contestService.findContestProblemById(cpId);
		mav.addObject("contestProblem", contestProblem);
		mav.addObject("mainPage", "contest-problem-detail.jsp");
		mav.setViewName("foreground/contest/contest-detail");
		return mav;
	}
	
	/**
	 * 查找比赛提交列表
	 */
	@RequestMapping(value="/{contestId}/submit/list/{page}",method=RequestMethod.GET)
	public ModelAndView getContestSubmits(@PathVariable("contestId") Integer contestId,
			@PathVariable("page") Integer page,HttpServletRequest request) {
		ModelAndView mav=new ModelAndView();
		String url=request.getContextPath()+"/contest/"+contestId+"/submit/list";
		Integer count=submitService.findContestSubmitCount(contestId);
		PageBean pageBean=new PageBean(10, page,count);
		String pagination=PageUtil.getPagination(url, pageBean);
		mav.addObject("pagination", pagination);
		List<Submit>submitList=submitService.findContestSubmits(contestId,pageBean);
		mav.addObject("submitList", submitList);
		mav.addObject("mainPage", "contest-problem-submit.jsp");
		mav.setViewName("foreground/contest/contest-detail");
		return mav;
	}
	
	/**
	 * 获取竞赛用户列表
	 * @param contestId
	 * @param page
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/{contestId}/user/list/{page}")
	public ModelAndView getContestUser(@PathVariable("contestId") Integer contestId,
			@PathVariable("page") Integer page,HttpServletRequest request) {
		ModelAndView mav=new ModelAndView();
		String url=request.getContextPath()+"/contest/"+contestId+"/user/list";
		Integer count=userService.findUserCountByContestId(contestId);
		PageBean pageBean=new PageBean(10, page,count);
		String pagination=PageUtil.getPagination(url, pageBean);
		mav.addObject("pagination", pagination);
		List<ContestUser>contestUser=userService.findContestUsers(contestId, pageBean);
		mav.addObject("contestUser", contestUser);
		mav.addObject("mainPage", "contest-user-list.jsp");
		mav.setViewName("foreground/contest/contest-detail");
		return mav;
	}
}
