package com.hhoj.judger.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.hhoj.judger.annotation.ValidatePermission;
import com.hhoj.judger.entity.PageBean;
import com.hhoj.judger.entity.Role;
import com.hhoj.judger.entity.User;
import com.hhoj.judger.service.UserService;
import com.hhoj.judger.util.PageUtil;
import com.hhoj.judger.util.ResponseUtil;

@Controller
@RequestMapping(value= "/manager/user")
public class AdminUserController {
	
	private static Logger logger=LoggerFactory.getLogger(AdminUserController.class);
	
	@Resource
	private UserService userService;
	

	@ValidatePermission(role=Role.ROOT)
	@RequestMapping("/list/{page}")
	public ModelAndView userList(@PathVariable("page") Integer page,HttpServletRequest request){
		if(page==null) {
			page=1;
		}
		ModelAndView mav=new ModelAndView();
		User user=new User();
		Integer count=userService.findCount(user);
		PageBean pageBean=new PageBean(10, page, count);
		String contextPath=request.getContextPath();
		String pagination=PageUtil.getPagination(contextPath+"/manager/user/list", pageBean);
		
		List<User> userList=userService.findUsers(user,pageBean);
		mav.addObject("pagination", pagination);
		mav.addObject("userList", userList);
		mav.addObject("mainPage", "user/list.jsp");
		mav.setViewName("manager");
		return mav;
	}
	
	
	@RequestMapping("/remove/{uid}")
	public void removeUser(@PathVariable("uid")Integer uid,HttpServletResponse response){
		Integer count=userService.removeUser(uid);
		JSONObject result=new JSONObject();
		result.put("success", true);
		result.put("count", count);
		ResponseUtil.write(result, response);
	}
}
