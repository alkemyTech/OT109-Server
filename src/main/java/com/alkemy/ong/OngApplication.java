package com.alkemy.ong;

import com.alkemy.ong.entities.Role;
import com.alkemy.ong.entities.User;
import com.alkemy.ong.repositories.UserRepository;
import com.alkemy.ong.services.RoleService;
import com.alkemy.ong.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;


import java.util.Random;

@SpringBootApplication
public class OngApplication implements ApplicationRunner {

    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleService roleService;
    @Autowired
    PasswordEncoder passwordEncoder;


    public static void main(String[] args) {
        SpringApplication.run(OngApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {


        if (roleService.findByName("ADMIN") == null) {
            Role role = new Role();
            role.setName("ADMIN");
            role.setDescription("Usuario con rol Administrador");
            roleService.create(role);

        }

        if (roleService.findByName("USER") == null) {
            Role role = new Role();
            role.setName("USER");
            role.setDescription("Usuario con rol User");
            roleService.create(role);

        }
        Optional<User> opt = userRepository.findByEmailIgnoreCase("admin@admin.com");
        if (!opt.isPresent()) {
            User user = new User();
            user.setEmail("admin@admin.com");
            user.setFirstName("Administrador");
            user.setLastName("Root");
            user.setPassword("admin");
            user.setRole(roleService.findByName("ADMIN"));

            userService.create(user);

        }
    }
}