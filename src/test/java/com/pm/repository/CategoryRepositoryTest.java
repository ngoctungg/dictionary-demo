package com.pm.repository;

import com.pm.entity.CategoryEntity;
import com.pm.entity.RoleEntity;
import com.pm.entity.UserEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void insert() throws Exception {
        CategoryEntity category = new CategoryEntity();
        category.setName("Demo");
        categoryRepository.save(category);
        assertNotNull(category.getId());
    }


}