package com.alkemy.ong;

import com.alkemy.ong.entities.Role;
import com.alkemy.ong.entities.User;
import com.alkemy.ong.exceptions.RoleServiceException;
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
        Role admin, userRole;
        try{
            roleService.findByName("ADMIN");
            roleService.findByName("USER");
        }catch(RoleServiceException ex){
            admin = new Role();
            admin.setName("ADMIN");
            admin.setDescription("Usuario con rol Administrador");
            roleService.create(admin);

            userRole = new Role();
            userRole.setName("USER");
            userRole.setDescription("Usuario con rol User");
            roleService.create(userRole);
        }finally {
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
}