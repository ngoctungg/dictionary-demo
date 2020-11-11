package com.pm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

	@RequestMapping(value = "/addRules", method = RequestMethod.GET)
	public String addRule1(@ModelAttribute RulesEntity ruless, BindingResult result,RedirectAttributes redirect,Model model,@RequestParam(name = "id",required = false) Integer id) {
		if (result.hasErrors()) {
			return "404";
		}
		redirect.addFlashAttribute("success", "Saved employee successfully!");
		if(id==null) {
			model.addAttribute("rule",new RulesEntity());
		}
		else {
			model.addAttribute("rule",rules.getRulesByID(id));
		}
		rules.addRules(ruless);
		return "addRules";
	}
	 @RequestMapping(value = "/addRules", method = RequestMethod.POST)
	  public String doAddEmployee(@ModelAttribute("rule") RulesEntity ruleEntity) {
		 rules.addRules(ruleEntity);
	    return "redirect:rules";
	  }
}
