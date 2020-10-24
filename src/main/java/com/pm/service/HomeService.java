package com.pm.service;

import com.pm.entity.CategoryEntity;
import com.pm.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HomeService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<CategoryEntity> getAllCategories(){
        List<CategoryEntity> categories = categoryRepository.findAllCategoriesAndPost();
        return categories;
    }
    
}
