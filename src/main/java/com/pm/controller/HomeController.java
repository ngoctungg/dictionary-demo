package com.pm.controller;

import com.pm.entity.PostEntity;
import com.pm.service.HomeService;
import com.pm.service.PostRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private HomeService service;

    @Autowired
    private PostRestService postRestService;

    @GetMapping({"/","/post","/post/*"})
    public String index(Model model){
        model.addAttribute("categories",service.getAllCategories());
        return "index";
    }

    @GetMapping("/search")
    public String searchPage(@RequestParam(name = "q") String query, Model model){
        if(query != null && query.trim().length() >= 3){
            List<PostEntity> posts = postRestService.searchPost(query);
            model.addAttribute("posts",posts);
        }
        return "searchpage";
    }

}
