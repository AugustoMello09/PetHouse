package io.gitHub.AugustoMello09.payment.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.RequestInterceptor;

@Configuration
public class AppConfig {
	
	@Bean
    RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            requestTemplate.header("User-Agent", "pethouse");
            requestTemplate.header("accept", "application/json");
        };
    }

}
