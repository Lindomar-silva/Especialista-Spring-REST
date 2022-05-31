package com.algaworks.algafood.di.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.di.modelo.Cliente;
import com.algaworks.algafood.di.notificacao.Notificador;

@Component
public class AtivacaoClienteService {
	
	@Qualifier(value = "sms")
	@Autowired
	private Notificador notificador;

	public void ativar(Cliente cliente) {
		cliente.Ativar();

		notificador.notificar(cliente, "Cadastro ativo!");

	}

}
