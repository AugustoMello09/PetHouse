package io.gitHub.AugustoMello09.payment.configs;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {

	private static final String[] PUBLIC = {"/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**", "/h2-console/**" };

	@Autowired
	private Environment env;

	@Autowired
	private SecurityFilter securityFilter;
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		if (Arrays.asList(env.getActiveProfiles()).contains("test")) {
			http.headers((headers) -> headers.frameOptions((frameOptions) -> frameOptions.disable()));
			http.authorizeHttpRequests(authorize -> authorize.requestMatchers(PUBLIC).permitAll());
		}
		http.csrf(csrf -> csrf.disable())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(authorize -> authorize.requestMatchers(HttpMethod.GET, PUBLIC).permitAll()
						.anyRequest().authenticated())
				.addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}

}
