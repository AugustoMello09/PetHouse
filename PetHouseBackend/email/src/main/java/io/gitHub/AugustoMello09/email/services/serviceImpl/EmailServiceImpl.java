package io.gitHub.AugustoMello09.email.services.serviceImpl;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import io.gitHub.AugustoMello09.email.infra.entities.Usuario;
import io.gitHub.AugustoMello09.email.services.EmailService;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

	private final JavaMailSender emailSender;

	@Value(value = "${spring.mail.username}")
	private String emailFrom;

	@Override
	public void enviarEmailBemVindo(Usuario user) {
		try {
			  MimeMessage message = emailSender.createMimeMessage();
			  MimeMessageHelper helper = new MimeMessageHelper(message, true);
			  
			  helper.setFrom(emailFrom);
			  helper.setSubject("PetHouse - PetShop");
			  helper.setTo(user.getEmail());
			  
			  String template = carregarTemplateEmail();
			  
			  template = template.replace("#{nome}", user.getNome());
			  
			  helper.setText(template, true); 
			  
			  emailSender.send(message);
			   
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	
	public String carregarTemplateEmail() throws IOException {
		ClassPathResource resource = new ClassPathResource("templates/bemVindo.html");
		return new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
	}

}
