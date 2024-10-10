package io.gitHub.AugustoMello09.PetHouse.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.License;

@Configuration
@OpenAPIDefinition(info = @Info(title = "Pethouse Microsservice", version = "v1", description = "Documentação do microsserviço Pethouse."))
public class OpenApi {
	
	@Bean
	OpenAPI api() {
		return new OpenAPI().components(new Components())
				.info(new io.swagger.v3.oas.models.info.Info().title("PetHouse Microsservice").version("v1")
						.license(new License().name("Apache 2.0").url("http://springdoc.org")));
	}

}
