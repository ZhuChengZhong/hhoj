package com.hhoj.judger.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/index")
public class JumpController {
	
	public String redirectToIndex() {
		return "redirect:index";
	}
	
	@RequestMapping("/manager")
	public String redirectToManager() {
		return "manager";
	}
}
