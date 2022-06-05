package com.algaworks.algafood.jpa;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.algaworks.algafood.AlgafoodApiApplication;
import com.algaworks.algafood.domain.repository.RestauranteRepository;

public class ConsultaRestauranteMain {
	public static void main(String[] args) {
		ApplicationContext context=new SpringApplicationBuilder(AlgafoodApiApplication.class)
				.web(WebApplicationType.NONE).run(args);
		
		RestauranteRepository restaurantes = context.getBean(RestauranteRepository.class);
		restaurantes.listarTodos().forEach(restaurante -> System.out.println(restaurante.getNome()));
	}
}
