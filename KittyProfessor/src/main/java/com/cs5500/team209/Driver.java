package com.cs5500.team209;

import com.cs5500.team209.model.Role;
import com.cs5500.team209.model.User;
import com.cs5500.team209.repository.*;
import com.cs5500.team209.storage.StorageProperties;
import com.cs5500.team209.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.apache.log4j.Logger;


import java.util.ArrayList;

/**
 * Driver program
 */
@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class Driver implements CommandLineRunner {
	final static Logger logger = Logger.getLogger(Driver.class);

	@Autowired
	private UserRepository repository;

	@Autowired
	private CourseRepository courseRepository;

	@Autowired
	private AssignmentRepository assignmentRepository;

	@Autowired
	private SubmissionRepository submissionRepository;

	@Autowired
	private StudentCourseRepository studentCourseRepository;

	@Autowired
	private ReportRepository reportRepository;

	@Autowired
	private RoleRepository roleRepository;

	public static void main(String[] args) {
		SpringApplication.run(Driver.class, args);
	}

	/**
	 * run some specific code once the SpringApplication has started
	 * @param args provides access to application arguments as a simple string array
	 * @throws Exception
     */
	@Override
	public void run(String... args) throws Exception {

		//Uncomment this to clean up db

		/*repository.deleteAll();
		courseRepository.deleteAll();
		assignmentRepository.deleteAll();
		roleRepository.deleteAll();
		studentCourseRepository.deleteAll();
		submissionRepository.deleteAll();
		reportRepository.deleteAll();*/

		// save a couple of users
		roleRepository.save(new Role("ROLE_ADMIN"));
		roleRepository.save(new Role("ROLE_USER"));
		ArrayList<Role> aliceRoles = new ArrayList<>();
		aliceRoles.add(new Role("ROLE_ADMIN"));
		repository.save(new User("alice", "test123", aliceRoles, "admin"));


		// fetch all users
		System.out.println("Customers found with findAll():");
		System.out.println("-------------------------------");
		for (User user : repository.findAll()) {
			System.out.println(user);
		}
		System.out.println();

		// fetch an individual user
		System.out.println("Users found with findByUsername('alice'):");
		System.out.println("--------------------------------");
		System.out.println(repository.findByUsername("alice"));
	}
}
