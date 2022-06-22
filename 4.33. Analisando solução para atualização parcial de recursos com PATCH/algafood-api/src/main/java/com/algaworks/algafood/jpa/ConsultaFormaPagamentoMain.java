package com.algaworks.algafood.jpa;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.algaworks.algafood.AlgafoodApiApplication;
import com.algaworks.algafood.domain.repository.FormaPagamentoRepository;

public class ConsultaFormaPagamentoMain {

	public static void main(String[] args) {
		ApplicationContext appContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
				.web(WebApplicationType.NONE).run(args);

		FormaPagamentoRepository repository = appContext.getBean(FormaPagamentoRepository.class);

		repository.listarTodos()
				.forEach(pagamentos -> System.out.printf("Forma de Pagamentos: %s\n", pagamentos.getDescricao()));

	}

}
