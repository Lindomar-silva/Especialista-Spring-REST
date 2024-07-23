package com.algaworks.algafood.core.squiggly;

import java.util.Arrays;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.bohnman.squiggly.Squiggly;
import com.github.bohnman.squiggly.web.RequestSquigglyContextProvider;
import com.github.bohnman.squiggly.web.SquigglyRequestFilter;

@Configuration
public class SquigglyConfig {

	@Bean
	protected FilterRegistrationBean<SquigglyRequestFilter> squigglyRequestFilter(ObjectMapper mappers) {
		Squiggly.init(mappers, new RequestSquigglyContextProvider("campos", null));

		var urlPatterns = Arrays.asList("/pedidos/*", "/restaurantes/*");

		var filterRegistration = new FilterRegistrationBean<SquigglyRequestFilter>();

		filterRegistration.setFilter(new SquigglyRequestFilter());
		filterRegistration.setUrlPatterns(urlPatterns);
		filterRegistration.setOrder(1);

		return filterRegistration;
	}
}
