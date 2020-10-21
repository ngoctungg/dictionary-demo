package com.pm.controller;

import com.pm.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EditController {

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/edit")
    public String html(Model model) {
        model.addAttribute("categories",categoryRepository.findAll());
        return "edit";
    }
}
