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
import com.hhoj.judger.entity.PageBean;
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
	
	
	/**
	 * 用户注册
	 * @param user 注册用户信息
	 * @param redirect 注册完后要跳转的页面
	 * @return
	 */
	@RequestMapping("/register.do")
	public ModelAndView addUser(User user,@RequestParam("redirect")String redirect,HttpServletRequest request){
		ModelAndView mav=new ModelAndView();
		boolean isSuccess=userService.addUser(user);
		if(isSuccess) {
			mav.addObject("mainPage", "/WEB-INF/jsp/user/"+redirect+".jsp");
			mav.setViewName("redirect:index");
			request.getSession().setAttribute("currentUser", user);
			logger.info("user register success :"+user);
		}else {
			mav.addObject("message", "该用户名或邮箱已被使用");
			mav.addObject("mainPage", "/WEB-INF/jsp/user/register.jsp");
			mav.setViewName("/user/register");
		}
		return mav;
	}
	
	
	/**
	 * 用户登录
	 * @param user
	 * @param redirect
	 * @param request
	 * @return
	 */
	public ModelAndView login(User user,@RequestParam("redirect")String redirect,HttpServletRequest request){
		ModelAndView mav=new ModelAndView();
		User u=userService.findUserByUserNameOrEmail(user);
		if(u !=null) {
			mav.addObject("mainPage", "/WEB-INF/jsp/user/"+redirect+".jsp");
			mav.setViewName("redirect:index");
			request.getSession().setAttribute("currentUser", u);
			logger.info("user logining :"+u);
		}else {
			mav.addObject("message", "用户名或密码错误");
			mav.addObject("mainPage", "/WEB-INF/jsp/user/login.jsp");
			mav.setViewName("/user/index");
		}
		return mav;
	}
	
	/**
	 * 用户退出
	 * @param request
	 * @return
	 */
	public ModelAndView logout(HttpServletRequest request){
		ModelAndView mav=new ModelAndView();
		User user=(User)request.getSession().getAttribute("currentUser");
		if(user !=null) {
			request.getSession().removeAttribute("currentUser");
			logger.info("user logout success :"+user);
		}
		mav.setViewName("/user/index");
		return mav;
	}
	
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
