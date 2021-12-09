package com.alkemy.ong;

import com.alkemy.ong.entities.Role;

import com.alkemy.ong.entities.User;

import com.alkemy.ong.services.RoleService;

import com.alkemy.ong.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Random;

@SpringBootApplication
public class OngApplication implements ApplicationRunner {

	@Autowired
	UserService userService;
	@Autowired
	RoleService roleService;


	public static void main(String[] args) {
		SpringApplication.run(OngApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
//		Role role = new Role("ADMIN");
//		roleService.create(role);
//		Role role2 = new Role("USER");
//		roleService.create(role2);

	}
}