package com.pm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RulesController {
	
	@GetMapping({"/rules", "/rules/*"})
	public String rules(Model model) {
		// TODO: Add add attributes and services
		return "rules";
	}
}
