package com.pm.repository;

import com.pm.entity.RoleEntity;
import com.pm.entity.UserEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.Arrays;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testJoin() throws Exception{
        UserEntity userEntity = new UserEntity();
        userEntity.setAccount("tungmn");
        userEntity.setPassword("hello");

        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setName("user");

        assertNull(userEntity.getId());
        assertNull(roleEntity.getId());

        userEntity.setRoles(Arrays.asList(roleEntity));
        roleEntity.setUsers(Arrays.asList(userEntity));

        userRepository.saveAndFlush(userEntity);

        assertNotNull(userEntity.getId());
        assertNotNull(roleEntity.getId());
    }

    @Test
    @Transactional
    public void selectJoinUserAndRole(){
        UserEntity user = userRepository.findAll().get(0);
        assertNotNull(user);
        assertEquals(user.getRoles().size(), 1);
        assertEquals("user",user.getRoles().get(0).getName().trim());
    }


}