package com.algaworks.algafood.jpa;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.algaworks.algafood.AlgafoodApiApplication;

public class ConsultaCozinhaMain {

	public static void main(String[] args) {
		ApplicationContext context = new SpringApplicationBuilder(AlgafoodApiApplication.class)
				.web(WebApplicationType.NONE).run(args);

		CadastroCozinha cadCozinha = context.getBean(CadastroCozinha.class);
		cadCozinha.listar().forEach(co -> System.out.println(co.getNome()));
	}

}
