package com.hhoj.judger.controller;

import java.util.List;

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
import com.hhoj.judger.entity.PageBean;
import com.hhoj.judger.entity.ProblemType;
import com.hhoj.judger.entity.TestPoint;
import com.hhoj.judger.service.ProblemTypeService;
import com.hhoj.judger.util.PageUtil;
import com.hhoj.judger.util.ResponseUtil;

@Controller
@RequestMapping(value="/manager/type")
public class AdminProblemTypeController {
	
	
	@Autowired
	private ProblemTypeService problemTypeService;
	
	private static Logger logger=LoggerFactory.getLogger(AdminProblemTypeController.class);
	
	
	/**
	 * 获取测试题类型列表
	 * @return
	 */
	@RequestMapping("/list")
	public ModelAndView list() {
		ModelAndView mav=new ModelAndView();
		int count=problemTypeService.findCount();
		List<ProblemType>list=problemTypeService.findProblemTypes();
		mav.addObject("typeList", list);
		mav.addObject("mainPage", "type/typelist.jsp");
		mav.setViewName("manager");
		return mav;
	}
	
	/**
	 * 跳转测试题类型添加页面
	 * @return
	 */
	@RequestMapping(value="/add",method= {RequestMethod.GET})
	public ModelAndView preAddProblemType() {
		ModelAndView mav=new ModelAndView();
		mav.addObject("mainPage", "type/typesave.jsp");
		mav.setViewName("manager");
		return mav;
	}
	
	/**
	 * 跳转测试题类型更新页面
	 * @param typeId
	 * @return
	 */
	@RequestMapping(value="/update/{typeId}",method= {RequestMethod.GET})
	public ModelAndView preUpdateProblemType(@PathVariable("typeId")Integer typeId) {
		ModelAndView mav=new ModelAndView();
		ProblemType problemType=problemTypeService.findProblemTypeById(typeId);
		mav.addObject("problemType", problemType);
		mav.addObject("mainPage", "type/typesave.jsp");
		mav.setViewName("manager");
		return mav;
	}
	
	
	/**
	 * 删除测试题类型
	 * @param typeId
	 * @param response
	 */
	@RequestMapping("/remove/{typeId}")
	public void removeProblemType(@PathVariable("typeId")Integer typeId,HttpServletResponse response){
		Integer count=problemTypeService.removeProblemType(typeId);
		logger.info("remove problemType : pointId "+typeId);
		JSONObject result=new JSONObject();
		result.put("success", true);
		result.put("count", count);
		ResponseUtil.write(result, response);
	}
	
	/**
	 * 更新或添加测试题
	 * @param problemType
	 * @return
	 */
	@RequestMapping(value="/save",method= {RequestMethod.POST})
	public ModelAndView saveProblemType(ProblemType problemType) {
		ModelAndView mav=new ModelAndView();
		if(problemType.getTypeId()!=null) {
			problemTypeService.updateProblemType(problemType);
		}else {
			problemTypeService.addProblemType(problemType);
		}
		mav.addObject("mainPage", "type/typelist.jsp");
		mav.setViewName("manager");
		return mav;
	}
}
