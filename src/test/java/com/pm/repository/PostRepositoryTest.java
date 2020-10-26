package com.pm.repository;

import com.pm.entity.CategoryEntity;
import com.pm.entity.PostEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

//    @Test
    public void insert() throws Exception {
        PostEntity postEntity = new PostEntity();
        postEntity.setTitle("demo");

        CategoryEntity category = new CategoryEntity();
        category.setId(2);

        postEntity.setCategory(category);
        postEntity.setContent("asdasd");
        postEntity.setSummary("sdsdf");

        postRepository.save(postEntity);
        assertNotNull(postEntity.getId());
    }

    @Test
    @Transactional
    public void search(){
        PostEntity postEntity = postRepository.findById(1).get();
        assertNotNull(postEntity);
        postEntity.getFiles().forEach(fileEntity -> System.out.print(fileEntity.getName()));
        assertEquals(2, postEntity.getFiles().size());
    }


}