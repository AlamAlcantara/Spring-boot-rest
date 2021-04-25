package com.example.users;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.users.entities.Role;
import com.example.users.entities.User;
import com.example.users.entities.UserInRole;
import com.example.users.repositories.RoleRepository;
import com.example.users.repositories.UserInRoleRepository;
import com.example.users.repositories.UserRepository;
import com.github.javafaker.Faker;

@SpringBootApplication
public class UsersAppApplication implements ApplicationRunner{
	
	@Autowired
	private Faker faker;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private UserInRoleRepository userInRoleRepository;
	
	
	private static final Logger log = LoggerFactory.getLogger(UsersAppApplication.class);
	

	public static void main(String[] args) {
		SpringApplication.run(UsersAppApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		
		Role[] roles = {new Role("ADMIN"), new Role ("USER"), new Role("SUPPORT")};
		
		for (Role role: roles) {
			roleRepository.save(role);
		}
		
		for(int i = 0; i <= 12 ; i++) {
			User user = new User();
			user.setPassword(faker.dragonBall().character());
			user.setUsername(faker.name().username());
			
			User createdUser = userRepository.save(user);
			
			UserInRole userInRole = new UserInRole();
			userInRole.setUser(createdUser);
			userInRole.setRole(roles[new Random().nextInt(3)]);
			
			userInRoleRepository.save(userInRole);
			
			log.info("User created Username: {}  Password: {}  Role: {}",
					user.getUsername(), user.getPassword(), userInRole.getRole().getName());
		}
		
	}

}
