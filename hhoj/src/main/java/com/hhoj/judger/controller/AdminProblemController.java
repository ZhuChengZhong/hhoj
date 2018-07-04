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
import com.hhoj.judger.annotation.ValidatePermission;
import com.hhoj.judger.entity.PageBean;
import com.hhoj.judger.entity.Problem;
import com.hhoj.judger.entity.ProblemType;
import com.hhoj.judger.entity.Role;
import com.hhoj.judger.entity.Submit;
import com.hhoj.judger.service.ProblemService;
import com.hhoj.judger.service.SubmitService;
import com.hhoj.judger.util.PageUtil;
import com.hhoj.judger.util.ResponseUtil;

@Controller
@RequestMapping("/manager/problem")
public class AdminProblemController {
	
	@Autowired
	private ProblemService problemService;
	
	@Autowired
	private SubmitService submitService;
	
	private static Logger logger=LoggerFactory.getLogger(AdminProblemController.class);
	
	@ValidatePermission(role=Role.MANAGER)
	@RequestMapping(value="/add",method= {RequestMethod.GET})
	public ModelAndView preAdd() {
		ModelAndView mav=new ModelAndView();
		mav.addObject("mainPage", "problem/add.jsp");
		mav.setViewName("manager");
		return mav;
	}
	

	@ValidatePermission(role=Role.MANAGER)
	@RequestMapping(value="/update/{pid}",method= {RequestMethod.GET})
	public ModelAndView preUpdate(@PathVariable(value="pid")Integer pid) {
		ModelAndView mav=new ModelAndView();
		Problem problem=problemService.findProblemById(pid);
		mav.addObject("problem", problem);
		mav.addObject("mainPage", "problem/add.jsp");
		mav.setViewName("manager");
		return mav;
	}
	
	
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
		String url=contextPath+"/manager/problem/list";
		String pagination=PageUtil.getPagination(url, pageBean);
		mav.addObject("pagination", pagination);
		mav.addObject("problemList", list);
		mav.addObject("mainPage", "problem/list.jsp");
		mav.setViewName("manager");
		return mav;
	}
	
	@ValidatePermission(role=Role.MANAGER)
	@RequestMapping(value="/save",method= {RequestMethod.POST})
	public void addProblem(Problem problem,HttpServletResponse response,@RequestParam(value="typeId")String typeId) {
		Date date=new Date();
		problem.setCreateTime(date);
		ProblemType type=new ProblemType();
		type.setTypeId(Integer.parseInt(typeId));
		problem.setType(type);
		problem.setPublish(0);
		problem.setAccepted(0);
		problem.setSubmited(0);
		Integer result;
		if(problem.getPid()!=null) {
			result=problemService.updateProblem(problem);
		}else {
			result=problemService.addProblem(problem);
		}
		JSONObject o=new JSONObject();
		if(result>0) {
			o.put("result", "success");
		}else {
			o.put("result", "error");
		}
		ResponseUtil.write(o, response);
	}
	
	
	@ValidatePermission(role=Role.MANAGER)
	@RequestMapping("/remove/{pid}")
	public void removeProblem(@PathVariable("pid")Integer pid,HttpServletResponse response){
		Integer count=problemService.removeProblem(pid);
		JSONObject result=new JSONObject();
		result.put("success", true);
		result.put("count", count);
		ResponseUtil.write(result, response);
	}
	
	@ValidatePermission(role=Role.MANAGER)
	@RequestMapping("/problem/submit")
	public ModelAndView submit(Submit submit) {
		ModelAndView mav=new ModelAndView();
		Integer result=submitService.addSubmit(submit);
		logger.info("new submit :"+submit);
		mav.setViewName("redirect:/submit/list/"+submit.getProblem().getPid());
		return mav;
	}
	
	@ValidatePermission(role=Role.MANAGER)
	@RequestMapping("/problem/update")
	public ModelAndView update(Problem problem) {
		ModelAndView mav=new ModelAndView();
		int result=problemService.updateProblem(problem);
		logger.info("update problem :"+problem);
		mav.setViewName("redirect:list");
		return mav;
	}
}
