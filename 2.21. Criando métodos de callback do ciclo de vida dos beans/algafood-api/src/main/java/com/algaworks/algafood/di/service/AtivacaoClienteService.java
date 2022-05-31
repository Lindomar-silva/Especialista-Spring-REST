package com.algaworks.algafood.di.service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;

import com.algaworks.algafood.di.modelo.Cliente;
import com.algaworks.algafood.di.notificacao.NivelUrgencia;
import com.algaworks.algafood.di.notificacao.Notificador;
import com.algaworks.algafood.di.notificacao.TipoDoNotificador;

//@Component
public class AtivacaoClienteService {

	@TipoDoNotificador(NivelUrgencia.URGENTE)
	@Autowired
	private Notificador notificador;

//	@PostConstruct
	public void init() {
		System.out.println("INIT... " + notificador);
	}

//	@PreDestroy
	public void destroy() {
		System.out.println("DESTROY...");
	}

	public void ativar(Cliente cliente) {
		cliente.Ativar();
		notificador.notificar(cliente, "Cadastro ativo!");
	}

}
