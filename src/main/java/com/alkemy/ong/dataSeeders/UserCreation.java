package com.alkemy.ong.dataSeeders;

import com.alkemy.ong.entities.Role;
import com.alkemy.ong.services.RoleService;
import com.alkemy.ong.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class UserCreation implements CommandLineRunner {
    @Autowired
    UserService userService;
    @Autowired
    RoleService roleService;


    public void run(String... args) throws Exception {

        Role roleUser = roleService.findByName("USER");
        Role roleAdmin = roleService.findByName("ADMIN");

        if (!userService.emailExists("juanperez@gmail.com")) {

            userService.create("Juan", "Perez", "juanperez@gmail.com", "juanperez", "", roleUser);
            userService.create("Marcos", "Sanchez", "marcossanchez@gmail.com", "marcossanchez", "", roleUser);

            userService.create("Monica", "Sala", "monicasala@gmail.com", "monicasala", "", roleAdmin);
            userService.create("Tamara", "Ceballos", "tamaraceballos@gmail.com", "tamaraceballos", "", roleAdmin);
        }
    }
}
