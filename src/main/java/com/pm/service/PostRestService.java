package com.pm.service;

import com.pm.entity.CategoryEntity;
import com.pm.entity.PostEntity;
import com.pm.model.PostModel;
import com.pm.model.ResponseMessage;
import com.pm.repository.CategoryRepository;
import com.pm.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostRestService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private PostRepository postRepository;

    @Transactional
    public ResponseMessage createPost(PostModel post){
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setName(post.getCategoryName());
        if(post.getCategoryId() == null){
            categoryRepository.save(categoryEntity);
        }else{
            categoryEntity.setId(post.getCategoryId());
        }

        PostEntity postEntity = new PostEntity();
        postEntity.setTitle(post.getTitle());
        postEntity.setContent(post.getContent());
        postEntity.setCategory(categoryEntity);
        postEntity.setSummary(post.getSummary());
        postRepository.save(postEntity);

        return new ResponseMessage("200","Create new post successfully");
    }

    public PostEntity getPostById(Integer id){
        PostEntity post = postRepository.getOne(id);
        return post;
    }

}

