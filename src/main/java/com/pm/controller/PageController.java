package com.pm.controller;

import com.pm.entity.PostEntity;
import com.pm.service.EditService;
import com.pm.service.HomeService;
import com.pm.service.PostRestService;
import com.pm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class PageController {
    @Autowired
    private EditService editService;
    @Autowired
    private HomeService homeService;
    @Autowired
    private PostRestService postRestService;
    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping({"/edit/*", "/edit"})
    public String html(Model model) {
        model.addAttribute("categories", editService.getAllCategories());
        return "edit";
    }

    @GetMapping({"/", "/post", "/post/*"})
    public String index(Model model) {
        model.addAttribute("categories", homeService.getAllCategories());
        return "index";
    }

    @GetMapping("/search")
    public String searchPage(@RequestParam(name = "q") String query, Model model) {
        if (query != null && query.trim().length() >= 3) {
            List<PostEntity> posts = postRestService.searchPost(query);
            model.addAttribute("posts", posts);
        }
        return "searchpage";
    }

    @GetMapping(("/user"))
    public String initialUserPage(Model model) {
        model.addAttribute("users",userService.getAllUser());
        return "addUser";
    }
}
