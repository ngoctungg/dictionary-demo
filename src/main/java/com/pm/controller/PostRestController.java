package com.pm.controller;


import com.pm.model.PostModel;
import com.pm.model.ResponseMessage;
import com.pm.service.PostRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.regex.Pattern;

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

    @GetMapping("/posts/{id}")
    public ResponseEntity getPost(@PathVariable(name = "id") String id){
        Pattern pattern = Pattern.compile("\\d+");
        if(!pattern.matcher(id).matches()){
            return ResponseEntity.ok().body(new ResponseMessage("404","Post is not found"));
        }
        int postId = Integer.parseInt(id);
        return ResponseEntity.ok().header("Cache-Control","max-age=60").body(restService.getPostById(postId));
    }

}
