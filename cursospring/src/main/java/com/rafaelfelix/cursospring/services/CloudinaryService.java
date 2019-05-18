package com.rafaelfelix.cursospring.services;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

@Service
public class CloudinaryService {
	
	private static final Logger LOG = LoggerFactory.getLogger(CloudinaryService.class);

	@Autowired
	private Cloudinary cloudinaryClient;
	
	@SuppressWarnings("rawtypes")
	public void uploadFile(String locationUrl) {
		Map uploadResult = new HashMap<>();
		File fileToUpload = new File(locationUrl);
		try {
			LOG.info("Upload Iniciado");
			uploadResult = cloudinaryClient.uploader().upload(fileToUpload, ObjectUtils.emptyMap());
			LOG.info("Upload Finallizado");
		} catch (IOException e) {
			LOG.info("Erro no upload da imagem: ".concat(e.getMessage()));
		}
		LOG.info("Resultado do Upload: ".concat(uploadResult.toString()));
	}
}
