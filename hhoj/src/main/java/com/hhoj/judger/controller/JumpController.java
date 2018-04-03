package com.hhoj.judger.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hhoj.judger.annotation.ValidatePermission;
import com.hhoj.judger.entity.Role;

@Controller
public class JumpController {
	@RequestMapping("/index")
	public String redirectToIndex() {
		return "index";
	}
	
	@ValidatePermission(role=Role.MANAGER)
	@RequestMapping("/manager")
	public String redirectToManager() {
		return "manager";
	}
	
	@RequestMapping("/authenticationFailure")
	public String authenticationFailure() {
		return "error/authentication";
	}
}
