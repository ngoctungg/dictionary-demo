package com.pm.service;

import com.pm.entity.CategoryEntity;
import com.pm.entity.PostEntity;
import com.pm.exception.NotFoundPostException;
import com.pm.model.PostModel;
import com.pm.model.ResponseMessage;
import com.pm.repository.CategoryRepository;
import com.pm.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class PostRestService {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private PostRepository postRepository;

    @Transactional
    public ResponseMessage createPost(PostModel post) {
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setName(post.getCategoryName());
        if (post.getCategoryId() == null) {
            categoryRepository.save(categoryEntity);
        } else {
            categoryEntity.setId(post.getCategoryId());
        }

        PostEntity postEntity = new PostEntity();
        postEntity.setTitle(post.getTitle());
        postEntity.setContent(post.getContent());
        postEntity.setCategory(categoryEntity);
        postEntity.setSummary(post.getSummary());
        postRepository.save(postEntity);

        return new ResponseMessage("200", "Create new post successfully");
    }

    public ResponseMessage getPostById(Integer id) {
        Optional<PostEntity> opPost = postRepository.findById(id);
        if (opPost.isPresent()) {
            PostEntity post = opPost.get();
            return new ResponseMessage<PostEntity>("200", String.valueOf(post.getId()), post);
        }
        return new ResponseMessage<PostEntity>("404", "Post is not found !!!");
    }

    @Transactional
    public ResponseMessage updatePostById(PostModel post) throws NotFoundPostException {
        Optional<PostEntity> opPost = postRepository.findById(post.getId());

        if (!opPost.isPresent()) {
            throw new NotFoundPostException();
        }

        PostEntity newPost = opPost.get();
        newPost.setTitle(post.getTitle());
        newPost.setContent(post.getContent());
        newPost.setSummary(post.getSummary());
        CategoryEntity newCategoryEntity = new CategoryEntity();
        if (post.getCategoryId() == null) {
            newCategoryEntity.setName(post.getCategoryName());
            categoryRepository.save(newCategoryEntity);
        } else {
            newCategoryEntity.setId(post.getCategoryId());
        }
        newPost.setCategory(newCategoryEntity);

        postRepository.save(newPost);
        return new ResponseMessage("200", "Update post successfully");
    }

    @Transactional
    public ResponseMessage deletePostById(Integer id){
        Optional<PostEntity> opPost = postRepository.findById(id);
        if(opPost.isPresent()){
            postRepository.delete(opPost.get());
        }
        return new ResponseMessage("200", "Delete post successfully");
    }

}

