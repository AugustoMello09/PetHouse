package io.gitHub.AugustoMello09.PetHouse.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.RequestInterceptor;

@Configuration
public class AppConfig {
	
	@Bean
	ModelMapper mapper() {
		return new ModelMapper();
	}
	
	@Bean
    RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            requestTemplate.header("User-Agent", "pethouse");
            requestTemplate.header("accept", "application/json");
            requestTemplate.header("access_token", "$aact_YTU5YTE0M2M2N2I4MTliNzk0YTI5N2U5MzdjNWZmNDQ6OjAwMDAwMDAwMDAwMDAwODg2NDM6OiRhYWNoXzk2NGQzMmUzLTU0NGItNGI4OC05NTc5LWYxZTM2OTc2OTBjMg==");
        };
    }
}
