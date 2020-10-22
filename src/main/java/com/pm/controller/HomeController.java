package com.pm.controller;

import com.pm.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    private HomeService service;

    @GetMapping({"/","/post","/post/*"})
    public String index(Model model){
        model.addAttribute("categories",service.getAllCategories());
        return "index";
    }

}
