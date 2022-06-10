package com.algaworks.algafood.jpa;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.algaworks.algafood.AlgafoodApiApplication;
import com.algaworks.algafood.domain.repository.CidadeRepository;

public class ConsultaCidadeMain {

	public static void main(String[] args) {
		ApplicationContext appContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
				.web(WebApplicationType.NONE).run(args);

		CidadeRepository repository = appContext.getBean(CidadeRepository.class);

		repository.listarTodos().forEach(cidade -> System.out.printf("Cidade: %s - Estado: %s\n", cidade.getNome(),
				cidade.getEstado().getNome()));

	}

}
