package com.pm.repository;

import com.pm.entity.RoleEntity;
import com.pm.entity.UserEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void setUp() throws Exception {
        RoleEntity role = new RoleEntity();
        role.setName("admin");
        assertNull(role.getId());
        this.roleRepository.save(role);
        assertNotNull(role.getId());
    }


}