package com.hhoj.judger.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.hhoj.judger.entity.Language;
import com.hhoj.judger.entity.PageBean;
import com.hhoj.judger.entity.Problem;
import com.hhoj.judger.entity.Submit;
import com.hhoj.judger.service.LanguageService;
import com.hhoj.judger.util.PageUtil;
import com.hhoj.judger.util.ResponseUtil;

@Controller
@RequestMapping("/language")
public class LanguageController {
	
	@Autowired
	private LanguageService languageService;
	private Logger logger=LoggerFactory.getLogger(LanguageController.class);
	@RequestMapping("/list")
	public ModelAndView list() {
		ModelAndView mav=new ModelAndView();
		List<Language>list=languageService.findLanguages();
		mav.addObject("languageList", list);
		mav.addObject("mainPage", "/WEB-INF/jsp/language/list.jsp");
		mav.setViewName("index");
		return mav;
	}
	
	@RequestMapping("/add")
	public ModelAndView addLanguage(Language language) {
		ModelAndView mav=new ModelAndView();
		Integer result=languageService.addLanguage(language);
		mav.setViewName("redirect:list");
		return mav;
	}
	
	@RequestMapping("/remove/{languageId}")
	public void removeLanguage(@PathVariable("languageId")Integer languageId,HttpServletResponse response){
		Integer count=languageService.removeLanguage(languageId);
		logger.info("remove language :id"+languageId);
		JSONObject result=new JSONObject();
		result.put("success", true);
		result.put("count", count);
		ResponseUtil.write(result, response);
	}
	

	
	@RequestMapping("/update")
	public ModelAndView update(Language language) {
		ModelAndView mav=new ModelAndView();
		int result=languageService.updateLanguage(language);
		logger.info("update language :"+language);
		mav.setViewName("redirect:list");
		return mav;
	}
}
