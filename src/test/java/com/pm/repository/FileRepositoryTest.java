package com.pm.repository;

import com.pm.entity.FileEntity;
import com.pm.entity.PostEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FileRepositoryTest {

    @Autowired
    private FileRepository fileRepository;

    @Test
    public void insert() throws Exception {
        FileEntity fileEntity = new FileEntity();
        fileEntity.setName("sdfsdf");
        fileEntity.setPath("sdfsdf/90");

        PostEntity postEntity = new PostEntity();
        postEntity.setId(1);
        fileEntity.setPost(postEntity);

        fileRepository.save(fileEntity);
        assertNotNull(fileEntity.getId());
    }


}