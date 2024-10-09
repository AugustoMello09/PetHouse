package io.gitHub.AugustoMello09.PetHouse.config;

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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {
	
	private static final String[] PUBLIC_POST = { "/v1/usuario" };

	private static final String[] PUBLIC = { "/h2-console/**" };

	private static final String[] PUBLIC_GET = { "/v1/plano/**", "/v1/produto/**", "/v1/usuario/buscarEmail", "/v1/usuario/info/{id}" };

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
				.authorizeHttpRequests(authorize -> authorize.requestMatchers(HttpMethod.POST, PUBLIC_POST).permitAll()
						.requestMatchers(HttpMethod.GET, PUBLIC_GET).permitAll().requestMatchers(PUBLIC).permitAll()
						.anyRequest().authenticated())
				.addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}

	@Bean
	BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
