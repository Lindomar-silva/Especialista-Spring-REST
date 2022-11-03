package com.algaworks.algafood.core.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		// (origins = { "http://www.algafood.local:8000", "http://127.0.0.1:5500" })
		registry.addMapping("/**")
			.allowedMethods("*");
//			.allowedOrigins("*")
//			.maxAge(30);
			
	}
}
