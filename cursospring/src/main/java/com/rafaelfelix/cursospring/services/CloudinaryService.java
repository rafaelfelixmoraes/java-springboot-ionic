package com.rafaelfelix.cursospring.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.rafaelfelix.cursospring.services.exceptions.FileException;

@Service
public class CloudinaryService {
	
	private static final Logger LOG = LoggerFactory.getLogger(CloudinaryService.class);

	@Autowired
	private Cloudinary cloudinaryClient;
	
	public URI uploadFile(MultipartFile multipartFile) {
		URI uri = null;
		URL url = null;
		try {
			String fileName = multipartFile.getOriginalFilename();
			url = new URL(uploadToCloudinary(multipartFile, fileName));
			uri = url.toURI();
		} catch (MalformedURLException | URISyntaxException e) {
			throw new FileException("Erro ao obter URI. Mensagem: ".concat(e.getMessage()));
		}
		
		return uri;
	}
	
	public URI uploadFile(File file, String fileName) {
		URI uri = null;
		URL url = null;
		try {
			url = new URL(uploadToCloudinary(file, fileName));
			uri = url.toURI();
		} catch (MalformedURLException | URISyntaxException e) {
			throw new FileException("Erro ao obter URI. Mensagem: ".concat(e.getMessage()));
		}
		
		return uri;
	}
	
	@SuppressWarnings("rawtypes")
	public String uploadToCloudinary(MultipartFile multipartFile, String fileName) {
		Map uploadResult = new HashMap<>();
		try {
			Map params = ObjectUtils.asMap("public_id", "profiles/".concat(fileName).split("\\.", 3)[0]);
			File convFile = multipartToFile(multipartFile);
			LOG.info("Upload Iniciado");
			uploadResult = cloudinaryClient.uploader().upload(convFile, params);
			LOG.info("Upload Finallizado");
		} catch (IOException e) {
			throw new FileException("Erro no upload da imagem: ".concat(e.getMessage()));
		}
		LOG.info("Resultado do Upload: ".concat(uploadResult.toString()));
		
		return uploadResult.get("url").toString();
	}
	
	@SuppressWarnings("rawtypes")
	public String uploadToCloudinary(File file, String fileName) {
		Map uploadResult = new HashMap<>();
		try {
			Map params = ObjectUtils.asMap("public_id", "profiles/".concat(fileName));
			LOG.info("Upload Iniciado");
			uploadResult = cloudinaryClient.uploader().upload(file, params);
			LOG.info("Upload Finallizado");
		} catch (IOException e) {
			throw new FileException("Erro no upload da imagem: ".concat(e.getMessage()));
		}
		LOG.info("Resultado do Upload: ".concat(uploadResult.toString()));
		
		return uploadResult.get("url").toString();
	}
	
	private File multipartToFile(MultipartFile file) {
    	File convFile = new File(file.getOriginalFilename());
		try {
			convFile.createNewFile();
			FileOutputStream fos = new FileOutputStream(convFile);
		    fos.write(file.getBytes());
		    fos.close();
		} catch (IllegalStateException | IOException e) {
			throw new FileException("Erro na conversão da imagem: ".concat(e.getMessage()));
		}
		return convFile;
	}
}
