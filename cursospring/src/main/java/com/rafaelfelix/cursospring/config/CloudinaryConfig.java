package com.rafaelfelix.cursospring.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

@Configuration
public class CloudinaryConfig {
	
	@Value("${cloudinary.cloud_name}")
	private String cloud_name;
	
	@Value("${cloudinary.api_key}")
	private String api_key;
	
	@Value("${cloudinary.api_secret}")
	private String api_secret;
	
	@Bean
	public Cloudinary cloudinaryClient() {
		Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
				  "cloud_name", cloud_name,
				  "api_key", api_key,
				  "api_secret", api_secret));
		
		return cloudinary;
	}

}
