package com.pm.controller;


import com.pm.model.PostModel;
import com.pm.model.ResponseMessage;
import com.pm.service.PostRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class PostRestController {
    @Autowired
    private PostRestService restService;

    @PostMapping("/posts")
    public ResponseEntity createPost(@RequestBody PostModel postModel){
        ResponseMessage message = restService.createPost(postModel);
        return ResponseEntity.ok().body(message);
    }

    @GetMapping("/posts")
    public String getPost(){
        return "string";
    }
}
