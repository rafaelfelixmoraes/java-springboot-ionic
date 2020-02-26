package com.rafaelfelix.cursospring.services;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.rafaelfelix.cursospring.services.exceptions.FileException;

@Service
public class S3Service {
	
	private Logger LOG = LoggerFactory.getLogger(S3Service.class);

	@Autowired
	private AmazonS3 s3Client;
	
	@Value("${s3.bucket}")
	private String bucketName;
	
	public void uploadSingleFile(String localFilePath) {
		try {
			File file = new File(localFilePath);
			LOG.info("Iniciando Upload");
			s3Client.putObject(new PutObjectRequest(bucketName, "Teste.png", file));
			LOG.info("Upload Finalizado");
		} catch (AmazonServiceException e) {
			LOG.info("Amazon Exception: " + e.getErrorMessage());
			LOG.info("Status Code: " + e.getStatusCode());
		}
	}
	
	public URI uploadFile(MultipartFile multipartFile) {
		try {
			String fileName = multipartFile.getOriginalFilename();
			InputStream is = multipartFile.getInputStream();
			String contentType = multipartFile.getContentType();
			Long fileSize = multipartFile.getSize();
			
			return uploadFile(is, fileName, contentType, fileSize);
		} catch (IOException e) {
			LOG.info("IOException: " + e.getMessage());
			
			throw new FileException("Erro de IO: " + e.getMessage());
		}
	}
	
	public URI uploadFile(InputStream is, String fileName, String contentType, Long fileSize) {
		try {
			ObjectMetadata metaData = new ObjectMetadata();
			metaData.setContentType(contentType);
			if(fileSize != null) {
				metaData.setContentLength(fileSize);
			}
			LOG.info("Iniciando Upload");
			s3Client.putObject(bucketName, fileName, is, metaData);
			LOG.info("Upload Finalizado");
			
			return s3Client.getUrl(bucketName, fileName).toURI();
		} catch (URISyntaxException e) {
			LOG.info("URI Exception: " + e.getMessage());
			
			throw new FileException("Erro ao converter URL para URI");
		}
	}
}
