package com.pm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.pm.entity.RulesEntity;
import com.pm.service.RulesService;

@Controller
public class RulesController {
	@Autowired
	private RulesService rules; 
	@GetMapping({"/rules", "/rules/*"})
	public List<RulesEntity> rules(Model model) {
		List<RulesEntity> list = rules.getAllRules();
		model.addAttribute("listRules", list);
		return list;
	}
}
