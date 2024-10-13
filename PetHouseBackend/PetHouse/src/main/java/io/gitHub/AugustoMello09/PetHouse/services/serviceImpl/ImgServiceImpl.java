package io.gitHub.AugustoMello09.PetHouse.services.serviceImpl;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;
import org.imgscalr.Scalr;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import io.gitHub.AugustoMello09.PetHouse.services.ImgService;
import io.gitHub.AugustoMello09.PetHouse.services.exceptions.FileException;

@Service
public class ImgServiceImpl implements ImgService {

	@Override
	public BufferedImage getJpgImageFromFile(MultipartFile uploadedFile) {
		
		String ext = FilenameUtils.getExtension(uploadedFile.getOriginalFilename()).toLowerCase();

		if (!"jpg".equals(ext) && !"jpeg".equals(ext)) {
			try {
				BufferedImage img = ImageIO.read(uploadedFile.getInputStream());
				img = convertToJpg(img);
				return img;
			} catch (IOException e) {
				throw new FileException("Erro ao ler arquivo: " + e.getMessage());
			}
		}
		try {
			return ImageIO.read(uploadedFile.getInputStream());
		} catch (IOException e) {
			throw new FileException("Erro ao ler arquivo: " + e.getMessage());
		}
	}

	@Override
	public BufferedImage convertToJpg(BufferedImage img) {
		BufferedImage jpgImage = new BufferedImage(img.getWidth(), img.getHeight(),
                BufferedImage.TYPE_INT_RGB);
        jpgImage.createGraphics().drawImage(img, 0, 0, Color.WHITE, null); 
        return jpgImage;
	}

	@Override
	public InputStream getInputStream(BufferedImage img, String extension) {
		try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(img, extension, os); 
            return new ByteArrayInputStream(os.toByteArray());
        } catch (IOException e) {
            throw new FileException("Erro ao obter InputStream da imagem: " + e.getMessage());
        }
	}

	@Override
	public BufferedImage cropSquare(BufferedImage sourceImg) {
		int min = Math.min(sourceImg.getHeight(), sourceImg.getWidth());
        return Scalr.crop(
            sourceImg,
            (sourceImg.getWidth() / 2) - (min / 2),
            (sourceImg.getHeight() / 2) - (min / 2),
            min,
            min
        );
	}

	@Override
	public BufferedImage resize(BufferedImage sourceImg, int size) {
		return Scalr.resize(sourceImg, Scalr.Method.ULTRA_QUALITY, size);
	}

}
