package com.algaworks.algafood.jpa;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.algaworks.algafood.AlgafoodApiApplication;
import com.algaworks.algafood.domain.repository.EstadoRepository;

public class ConsultaEstadoMain {

	public static void main(String[] args) {
		ApplicationContext appContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
				.web(WebApplicationType.NONE).run(args);

		EstadoRepository repository = appContext.getBean(EstadoRepository.class);

		repository.listar()
			.forEach(estado -> System.out.printf("Estado: %s\n", estado.getNome()));

	}

}
