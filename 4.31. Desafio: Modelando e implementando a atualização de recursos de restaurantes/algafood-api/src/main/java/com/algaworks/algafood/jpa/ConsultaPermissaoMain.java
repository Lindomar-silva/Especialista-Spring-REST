package com.algaworks.algafood.jpa;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.algaworks.algafood.AlgafoodApiApplication;
import com.algaworks.algafood.domain.repository.PermissaoRepository;

public class ConsultaPermissaoMain {

	public static void main(String[] args) {
		ApplicationContext appContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
				.web(WebApplicationType.NONE).run(args);

		PermissaoRepository repository = appContext.getBean(PermissaoRepository.class);

		repository.listarTodos().forEach(permissao -> System.out.printf("Permsão: %s -> %s\n",
				permissao.getNome(), permissao.getDescricao()));
	}

}
