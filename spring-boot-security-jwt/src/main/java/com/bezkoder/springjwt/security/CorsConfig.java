package com.bezkoder.springjwt.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();

		// Allow all origins
		config.addAllowedOrigin("*");

		// Allow all methods (GET, POST, PUT, DELETE, etc.)
		config.addAllowedMethod("*");

		// Allow all headers
		config.addAllowedHeader("*");

		source.registerCorsConfiguration("/**", config);

		return new CorsFilter(source);
	}
}
