package com.example.digitallibrary;

import com.example.digitallibrary.dto.CreateAdminRequest;
import com.example.digitallibrary.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DigitalLibraryApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(DigitalLibraryApplication.class, args);
	}

	@Autowired
	AdminService adminService;

	@Override
	public void run(String... args) throws Exception {

		//this is creating an admin so run it only at once , after that comment it

		adminService.create(
				CreateAdminRequest.builder()
						.name("Dev")
						.username("dev@gmail.com")
						.password("dev123")
						.build()
		);

	}
}
