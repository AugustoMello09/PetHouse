package io.gitHub.AugustoMello09.PetHouse.services;

import java.awt.image.BufferedImage;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

public interface ImgService {

	BufferedImage getJpgImageFromFile(MultipartFile uploadedFile);

	BufferedImage convertToJpg(BufferedImage img);

	InputStream getInputStream(BufferedImage img, String extension);

	BufferedImage cropSquare(BufferedImage sourceImg);

	BufferedImage resize(BufferedImage sourceImg, int size);

}
