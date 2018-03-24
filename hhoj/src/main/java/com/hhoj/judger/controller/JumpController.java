package com.hhoj.judger.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/index")
public class JumpController {
	@RequestMapping("/i")
	public String redirectToIndex() {
		return "index";
	}
	
	@RequestMapping("/manager")
	public String redirectToManager() {
		return "manager";
	}
}
