package com.alkemy.ong;

import com.alkemy.ong.entities.Role;
import com.alkemy.ong.entities.TestimonialEntity;
import com.alkemy.ong.entities.User;
import com.alkemy.ong.repositories.TestimonialRepository;
import com.alkemy.ong.services.RoleService;
import com.alkemy.ong.services.TestimonialService;
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

			Role role = new Role("ADMIN");
			roleService.create(role);



//			User user = new User();
//			user.setEmail("admin@admin.com");
//			user.setPassword("admin");
//			user.setLastName("unUser");
//			user.setFirstName("Juan");
//			user.setRole(roles[0]);
			userService.create("Juan", "Perez", "admin@admin.com", "admin", "", role );


	}
}