package io.gitHub.AugustoMello09.PetHouse.config;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.RequestInterceptor;

@Configuration
public class AppConfig {

	@Value("${acess.token}")
	private String token;

	@Bean
	ModelMapper mapper() {
		return new ModelMapper();
	}

	@Bean
	RequestInterceptor requestInterceptor() {
		return requestTemplate -> {
			requestTemplate.header("accept", "application/json");
			requestTemplate.header("access_token", token);
			requestTemplate.header("content-type", "application/json");
		};
	}
}
