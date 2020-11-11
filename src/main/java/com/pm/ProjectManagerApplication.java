package com.pm;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.pm.model.UserModel;
import com.pm.repository.UserRepository;
import com.pm.service.FileService;
import com.pm.service.UserService;

@SpringBootApplication
public class ProjectManagerApplication implements CommandLineRunner {

    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;
    @Autowired
    FileService fileService;

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
        fileService.initFolder();
    }
}
