package com.pm;


import com.pm.model.UserModel;
import com.pm.repository.UserRepository;
import com.pm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.orm.jpa.EntityManagerHolder;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

@SpringBootApplication
public class ProjectManagerApplication implements CommandLineRunner {

    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;

    public static void main(String[] args) {
        SpringApplication.run(ProjectManagerApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        if (!userRepository.existsByAccountEquals("admin")) {
            UserModel userModel = new UserModel();
            userModel.setAccount("admin");
            userModel.setPassword("admin");
            userModel.setRole(1);
            userService.registerNewUserAccount(userModel);
        }
    }
}
