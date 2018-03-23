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
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.hhoj.judger.entity.Problem;
import com.hhoj.judger.entity.ProblemType;
import com.hhoj.judger.entity.TestPoint;
import com.hhoj.judger.service.TestPointService;
import com.hhoj.judger.util.ResponseUtil;
import com.hhoj.judger.util.StringUtil;

@Controller
@RequestMapping("/testpoint")
public class TestPointController {
	
	
	@Autowired
	private TestPointService testPointService;
	
	private static Logger logger=LoggerFactory.getLogger(TestPointController.class);
	
	/**
	 * 跳转到添加测试点页面
	 * @param pid
	 * @return
	 */
	@RequestMapping(value="/add/{pid}",method= {RequestMethod.GET})
	public ModelAndView preAdd(@PathVariable(value="pid")Integer pid) {
		ModelAndView mav=new ModelAndView();
		mav.addObject("mainPage", "testpoint/save.jsp");
		mav.addObject("pid",pid);
		mav.setViewName("manager");
		return mav;
	}
	
	/**
	 * 跳转到更新页面
	 * @param pointId
	 * @return
	 */
	@RequestMapping(value="/update/{pointId}",method= {RequestMethod.GET})
	public ModelAndView preUpdate(@PathVariable(value="pointId")Integer pointId) {
		ModelAndView mav=new ModelAndView();
		TestPoint testPoint=testPointService.findTestPointById(pointId);
		mav.addObject("testPoint", testPoint);
		mav.addObject("pid", testPoint.getPid());
		mav.addObject("mainPage", "testpoint/save.jsp");
		mav.setViewName("manager");
		return mav;
	}
	
	/**
	 * 更新或添加测试点
	 * @param testPoint
	 * @return
	 */
	@RequestMapping(value="/save",method= {RequestMethod.POST})
	public ModelAndView save(TestPoint testPoint) {
		ModelAndView mav=new ModelAndView();
		if(testPoint.getPointId()!=null) {
			testPointService.updateTestPoint(testPoint);
		}else {
			testPointService.addTestPoint(testPoint);
		}
		mav.addObject("mainPage", "testpoint/list.jsp");
		mav.setViewName("manager");
		return mav;
	}
	
	/**
	 * 查询指定问题的测试点列表
	 * @param pid
	 * @return
	 */
	@RequestMapping(value="/list/{pid}",method= {RequestMethod.GET})
	public ModelAndView list(@PathVariable("pid")Integer pid) {
		ModelAndView mav=new ModelAndView();
		List<TestPoint>list= testPointService.findTestPoints(pid);
		mav.addObject("testPointList", list);
		mav.addObject("pid", pid);
		mav.addObject("mainPage", "testpoint/list.jsp");
		mav.setViewName("manager");
		return mav;
	}
	
	/**
	 * 测试点删除
	 * @param pointId
	 * @param response
	 */
	@RequestMapping("/remove/{pointId}")
	public void removeProblemType(@PathVariable("pointId")Integer pointId,HttpServletResponse response){
		Integer count=testPointService.removeTestPoint(pointId);
		logger.info("remove testpoint : pointId "+pointId);
		JSONObject result=new JSONObject();
		result.put("success", true);
		result.put("count", count);
		ResponseUtil.write(result, response);
	}
}
