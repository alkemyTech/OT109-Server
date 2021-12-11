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
            userService.create("Ana Paula", "Gonzalez", "anapaulagonzalez@gmail.com", "anapaulagonzalez", "", roleUser);
            userService.create("Pablo", "Caceres", "pablocaceres@gmail.com", "pablocaceres", "", roleUser);
            userService.create("Cecilia", "Rodriguez", "ceciliarodriguez@gmail.com", "ceciliarodriguez", "", roleUser);
            userService.create("Mauro", "Viña", "maurovina@gmail.com", "maurovina", "", roleUser);
            userService.create("Pedro", "Nuñez", "pedronunez@gmail.com", "pedronunez", "", roleUser);
            userService.create("Josefina", "Ramirez", "josefinaramirez@gmail.com", "josefinaramirez", "", roleUser);
            userService.create("Gimena", "Roth", "gimenaroth@gmail.com", "gimenaroth", "", roleUser);
            userService.create("Leandro", "Gomez", "leandrogomez@gmail.com", "leandrogomez", "", roleUser);

            userService.create("Tamara", "Ceballos", "tamaraceballos@gmail.com", "tamaraceballos", "", roleAdmin);
            userService.create("Hernan", "Retamar", "hernanretamar@gmail.com", "hernanretamar", "", roleAdmin);
            userService.create("Jazmin", "Cabello", "jazmincabello@gmail.com", "jazmincabello", "", roleAdmin);
            userService.create("Lucas", "Bracamonte", "lucasbracamonte@gmail.com", "lucasbracamonte", "", roleAdmin);
            userService.create("Salvador", "Correa", "salvadorcorrea@gmail.com", "salvadorcorrea", "", roleAdmin);
            userService.create("Bautista", "Pearson", "bautistapearson@gmail.com", "bautistapearson", "", roleAdmin);
            userService.create("Silvia", "Politi", "silviapoliti@gmail.com", "silviapoliti", "", roleAdmin);
            userService.create("Candelaria", "Luna", "candelarialuna@gmail.com", "candelarialuna", "", roleAdmin);
            userService.create("Monica", "Sala", "monicasala@gmail.com", "monicasala", "", roleAdmin);
            userService.create("Diego", "Peloso", "diegopeloso@gmail.com", "diegopeloso", "", roleAdmin);
        }
    }
}
