package com.hhoj.judger.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.hhoj.judger.entity.PageBean;
import com.hhoj.judger.entity.Role;
import com.hhoj.judger.entity.User;
import com.hhoj.judger.service.UserService;
import com.hhoj.judger.util.JavaMailUtil;
import com.hhoj.judger.util.PageUtil;
import com.hhoj.judger.util.PropertiesUtil;
import com.hhoj.judger.util.ResponseUtil;
import com.hhoj.judger.util.StringUtil;

/**
 * 
 * @author zhu
 *
 */

@Controller
@RequestMapping(value= "/user")
public class UserController {
	
	private static Logger logger=LoggerFactory.getLogger(UserController.class);
	
	@Resource
	private UserService userService;
	
	
	/**
	 * 用户注册
	 * @param user 注册用户信息
	 * @param redirect 注册完后要跳转的页面
	 * @return
	 */
	@RequestMapping(value="/register",method= {RequestMethod.POST})
	public void addUser(@RequestParam("randomcode") String randomcode,User user,
			HttpServletRequest request,HttpServletResponse response){
		String message="";
		boolean isSuccess=false;
		String realRandomcode=(String)request.getSession().getAttribute("randomcode");
		if(StringUtil.isEmpty(randomcode)||
				StringUtil.isEmpty(realRandomcode)||
				!randomcode.equals(realRandomcode)) {
			message="验证码错误";
		}else if(userService.findUserByUserName(user.getUserName())!=null) {
			message="该用户名已被使用";
		}else if(userService.findUserByEmail(user.getEmail())!=null) {
			message="该邮箱已被使用";
		}else {
			user.setRegistTime(new Date());
			user.setRole(0);
			user.setSolved(0);
			user.setSubmited(0);
			user.setAccepted(0);
			user.setLastLoginTime(new Date());
			isSuccess=userService.addUser(user);
		}
		
		JSONObject result=new JSONObject();
		if(isSuccess) {
			logger.info("user register success :"+user);
			user.setPassword(null);
			request.getSession().setAttribute("currentUser", user);
			result.put("redirect", "/hhoj/index/i");
			result.put("success", true);
			/**
			 * 向用户发送激活邮件
			 */
			String url="http://localhost:8080/hhoj/user/active/"+user.getUid();
			String content="欢迎使用HHOJ<br> <a href='"+url+"'>点击链接激活帐号</a>";
			boolean sendSuccess=JavaMailUtil.sendActiveInfo(PropertiesUtil.getParam("emial.mailServer"), 
					PropertiesUtil.getParam("email.loginAccount"), PropertiesUtil.getParam("email.loginAuthCode"),
					PropertiesUtil.getParam("email.loginAccount"), new String[] {user.getEmail()}, "HHOJ帐号激活",
					content, PropertiesUtil.getParam("email.emailContentType"));
			if(!sendSuccess) {
				logger.error("send active email failed!");
			}
			
			
		}else {
			result.put("success", false);
			result.put("message", message);
		}
		
		ResponseUtil.write(result, response);
	}
	
	
	/**
	 * 激活账户
	 * @param uid
	 * @return
	 */
	@RequestMapping("/active/{uid}")
	public ModelAndView activeUser(@PathVariable("uid")Integer uid) {
		ModelAndView mav=new ModelAndView();
		User user=userService.findUserByUid(uid);
		if(user!=null&&user.getRole()==Role.NOT_ACTIVE) {
			User activeUser=new User();
			activeUser.setUid(user.getUid());
			activeUser.setRole(Role.COMMON);
			userService.updateUser(activeUser);
		}
		mav.setViewName("index");
		return mav;
	}
	
	
	/**
	 * 跳转到注册界面
	 * @return
	 */
	@RequestMapping(value="/register",method= {RequestMethod.GET})
	public String preRegister() {
		return "user/register";
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
		User u=userService.findUserByUserName(user.getUserName());
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
