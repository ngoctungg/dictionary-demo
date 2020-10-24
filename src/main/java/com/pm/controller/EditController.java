package com.pm.controller;

import com.pm.repository.CategoryRepository;
import com.pm.service.EditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EditController {

    @Autowired
    private EditService service;

    @GetMapping({"/edit/*","/edit"})
    public String html(Model model) {
        model.addAttribute("categories",service.getAllCategories());
        return "edit";
    }
}
