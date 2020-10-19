package com.pm.controller;

import com.pm.entity.RoleEntity;
import com.pm.entity.UserEntity;
import com.pm.repository.RoleRepository;
import com.pm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@SpringBootApplication
@RestController
public class HelloController {

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private UserRepository userRepository;

	@GetMapping("/")
	@Transactional
	public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {

		UserEntity userEntity = new UserEntity();
		userEntity.setAccount("tungmn");
		userEntity.setPassword("hello");

		RoleEntity roleEntity = new RoleEntity();
		roleEntity.setName("user");

		userEntity.setRoles(Arrays.asList(roleEntity));
		roleEntity.setUsers(Arrays.asList(userEntity));

		userRepository.saveAndFlush(userEntity);
		roleRepository.saveAndFlush(roleEntity);
		return String.format("Hello %s!", name);
		//
	}
}