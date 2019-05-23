package com.rafaelfelix.cursospring.services;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;
import org.imgscalr.Scalr;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.rafaelfelix.cursospring.services.exceptions.FileException;

@Service
public class ImageService {

	public BufferedImage getJpgImageFromFile(MultipartFile uploadedFile) {
		String ext = FilenameUtils.getExtension(uploadedFile.getOriginalFilename());
		try {
			if(!"png".equals(ext) && !"jpg".equals(ext)) {
				throw new FileException("Somente imagens JPG ou PNG são permitidas");
			}
		
			BufferedImage img = ImageIO.read(uploadedFile.getInputStream());
			if("png".equals(ext)) {
				img = pngToJpg(img);
			}
			return img;
		} catch (IOException e) {
			throw new FileException("Erro ao ler arquivo");
		}
	}

	public BufferedImage pngToJpg(BufferedImage img) {
			BufferedImage newImg = new BufferedImage(img.getWidth(),
													 img.getHeight(), 
													 BufferedImage.TYPE_INT_RGB);
			
			newImg.createGraphics().drawImage(img, 0, 0, Color.WHITE, null);
			
			return newImg;
	}
	
	public File getFile(BufferedImage img, String fileName) {
		try {
			File file = new File(fileName);
			ImageIO.write(img, "JPG", file);
			
			return file;
		} catch (IOException e) {
			throw new FileException("Erro ao obter arquivo");
		}
	}
	
	public BufferedImage cropSquareImage(BufferedImage image) {
		int min = (image.getHeight() <= image.getWidth()) ? image.getHeight() : image.getWidth();
		return Scalr.crop(image, 
						  (image.getWidth() / 2) - (min / 2), 
						  (image.getHeight() / 2) - (min / 2), 
						  min, min);
	}
	
	public BufferedImage resize(BufferedImage image, Integer size) {
		return Scalr.resize(image, Scalr.Method.ULTRA_QUALITY, size);
	}
}
