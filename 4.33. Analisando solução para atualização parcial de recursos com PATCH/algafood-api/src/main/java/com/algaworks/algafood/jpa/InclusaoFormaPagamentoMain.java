package com.algaworks.algafood.jpa;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.algaworks.algafood.AlgafoodApiApplication;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.repository.FormaPagamentoRepository;

public class InclusaoFormaPagamentoMain {

	public static void main(String[] args) {
		
		ApplicationContext appContext=new SpringApplicationBuilder(AlgafoodApiApplication.class)
				.web(WebApplicationType.NONE).run(args);
		
		FormaPagamentoRepository repository = appContext.getBean(FormaPagamentoRepository.class);
		
		FormaPagamento pagamento1 = new FormaPagamento();
		FormaPagamento pagamento2 = new FormaPagamento();
			
		pagamento1.setDescricao("Pagamento parcelado");
		pagamento2.setDescricao("Pagamento a visata");
		
		repository.salvar(pagamento1);
		repository.salvar(pagamento2);
	}

}
