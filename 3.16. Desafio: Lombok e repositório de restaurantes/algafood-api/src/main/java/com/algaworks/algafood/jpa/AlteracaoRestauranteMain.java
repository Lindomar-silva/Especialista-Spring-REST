package com.algaworks.algafood.jpa;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.algaworks.algafood.AlgafoodApiApplication;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;

public class AlteracaoRestauranteMain {
	public static void main(String[] args) {
		ApplicationContext context = new SpringApplicationBuilder(AlgafoodApiApplication.class)
				.web(WebApplicationType.NONE).run(args);

		RestauranteRepository restaurantes = context.getBean(RestauranteRepository.class);

		Restaurante restaurante = restaurantes.buscar(1L);
		restaurante.setNome("Panela Velha");

		restaurante = restaurantes.salvar(restaurante);
		System.out.printf("Restaurante: %d - %s Frete: %.2f\n", restaurante.getId(), restaurante.getNome(),
				restaurante.getTaxaFrete());
	}
}
