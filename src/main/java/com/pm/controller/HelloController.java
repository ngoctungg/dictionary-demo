package com.pm.controller;

import com.pm.entity.CategoryEntity;
import com.pm.entity.PostEntity;
import com.pm.repository.CategoryRepository;
import com.pm.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


@RestController
public class HelloController {

    @Autowired
	CategoryRepository categoryRepository;

    @GetMapping("/post")
    public ResponseEntity hello(@RequestParam(value = "name", defaultValue = "World") String name) {
		List<String> categoies = new ArrayList<>();
		categoryRepository.findAll().forEach(categoryEntity -> categoies.add(categoryEntity.getName()));
        return ResponseEntity.ok().body(categoies);
    }
}