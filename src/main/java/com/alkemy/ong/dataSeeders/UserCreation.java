package com.alkemy.ong.dataSeeders;

import com.alkemy.ong.entities.Role;
import com.alkemy.ong.entities.User;
import com.alkemy.ong.services.RoleService;
import com.alkemy.ong.services.UserService;
import com.alkemy.ong.services.impl.RoleServiceImpl;
import com.alkemy.ong.services.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class UserCreation implements CommandLineRunner {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private RoleService roleService;


    public void run(String... args) throws Exception {
        
        Role roleUser = roleService.findByName("USER");
        Role roleAdmin = roleService.findByName("ADMIN");
        if (!userService.emailExists("juanperez@gmail.com")) {
            
            userService.create(User.builder().firstName("Juan").lastName("Perez").email("juanperez@gmail.com").password("juanperez").photo("").role(roleUser).build());
            userService.create(User.builder().firstName("Marcos").lastName("Sanchez").email("marcossanchez@gmail.com").password("marcossanchez").photo("").role(roleUser).build());

            userService.create(User.builder().firstName("Monica").lastName("Sala").email("monicasala@gmail.com").password("monicasala").photo("").role(roleAdmin).build());
            userService.create(User.builder().firstName("Tamara").lastName("Ceballos").email("tamaraceballos@gmail.com").password("tamaraceballos").photo("").role(roleAdmin).build());
        }
    }
}
