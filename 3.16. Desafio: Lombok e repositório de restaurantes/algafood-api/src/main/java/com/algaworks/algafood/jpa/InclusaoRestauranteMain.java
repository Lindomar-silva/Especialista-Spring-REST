package com.algaworks.algafood.jpa;

import java.math.BigDecimal;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.algaworks.algafood.AlgafoodApiApplication;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;

public class InclusaoRestauranteMain {
	public static void main(String[] args) {
		ApplicationContext context = new SpringApplicationBuilder(AlgafoodApiApplication.class)
				.web(WebApplicationType.NONE).run(args);

		RestauranteRepository restaurantes = context.getBean(RestauranteRepository.class);

		Restaurante restaurante = new Restaurante();
		restaurante.setNome("Novo Paladar");
		restaurante.setTaxaFrete(new BigDecimal(26.4));

		restaurante = restaurantes.salvar(restaurante);

		System.out.printf("Restaurante: %d - %s Frete: %.2f\n", restaurante.getId(), restaurante.getNome(),
				restaurante.getTaxaFrete());
	}
}
