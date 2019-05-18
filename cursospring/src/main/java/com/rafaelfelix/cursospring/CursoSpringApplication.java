package com.rafaelfelix.cursospring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.rafaelfelix.cursospring.services.CloudinaryService;

@SpringBootApplication
public class CursoSpringApplication implements CommandLineRunner{
	
	@Autowired
	private CloudinaryService cloudinaryService;

	public static void main(String[] args) {
		SpringApplication.run(CursoSpringApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		String fileUrl = "/Users/rafael.felix1ibm.com/Downloads/spring_logo.png";
		cloudinaryService.uploadFile(fileUrl);
	}

}

