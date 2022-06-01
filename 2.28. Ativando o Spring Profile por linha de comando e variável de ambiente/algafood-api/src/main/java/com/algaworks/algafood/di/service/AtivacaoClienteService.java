package com.algaworks.algafood.di.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.di.modelo.Cliente;

@Component
public class AtivacaoClienteService {

	@Autowired
	private ApplicationEventPublisher publisher;

	public void ativar(Cliente cliente) {
		cliente.Ativar();

		publisher.publishEvent(new ClienteAtivadoEvent(cliente));
	}

}
