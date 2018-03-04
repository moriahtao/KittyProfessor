package com.cs5500.team209;

import com.cs5500.team209.model.Role;
import com.cs5500.team209.model.User;
import com.cs5500.team209.repository.RoleRepository;
import com.cs5500.team209.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;

@SpringBootApplication
public class Driver implements CommandLineRunner {
	@Autowired
	private UserRepository repository;

	@Autowired
	private RoleRepository roleRepository;

	public static void main(String[] args) {
		SpringApplication.run(Driver.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		repository.deleteAll();
		roleRepository.deleteAll();

		// save a couple of customers
		roleRepository.save(new Role("ROLE_ADMIN"));
		roleRepository.save(new Role("ROLE_USER"));
		ArrayList<Role> aliceRoles = new ArrayList<>();
		aliceRoles.add(new Role("ROLE_ADMIN"));
		repository.save(new User("alice", "test123", aliceRoles));
		repository.save(new User("bob", "test123"));


		// fetch all customers
		System.out.println("Customers found with findAll():");
		System.out.println("-------------------------------");
		for (User user : repository.findAll()) {
			System.out.println(user);
		}
		System.out.println();

		// fetch an individual customer
		System.out.println("Users found with findByUsername('alice'):");
		System.out.println("--------------------------------");
		System.out.println(repository.findByUsername("alice"));

		System.out.println("Users found with findByUsername('bob'):");
		System.out.println("--------------------------------");

	}
}