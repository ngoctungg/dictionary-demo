package com.pm;


import com.pm.entity.RoleEntity;
import com.pm.entity.UserEntity;
import com.pm.repository.RoleRepository;
import com.pm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.Collections;

@SpringBootApplication
public class ProjectManagerApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(ProjectManagerApplication.class, args);
    }

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;
    @Override
    public void run(String... args) throws Exception {
        if(!userRepository.existsById(1)) {
            RoleEntity admin = new RoleEntity();
            admin.setName("admin");
            RoleEntity user = new RoleEntity();
            user.setName("user");
            roleRepository.save(admin);
            roleRepository.save(user);


            UserEntity userEntity = new UserEntity();
            userEntity.setAccount("admin");
            userEntity.setPassword(passwordEncoder.encode("admin"));
            userEntity.setRoles(Collections.singletonList(admin));
            userRepository.save(userEntity);
        }

    }
}
