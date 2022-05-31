package com.algaworks.algafood.di.notificacao;

import org.springframework.stereotype.Component;

import com.algaworks.algafood.di.modelo.Cliente;

@TipoDoNotificador(NivelUrgencia.URGENTE)
@Component
public class NotificadorEmail implements Notificador {

	@Override
	public void notificar(Cliente cliente, String msg) {

		System.out.printf("Notificação %s através do e-mail %s: %s\n", 
				cliente.getNome(), cliente.getEmail(), msg);
	}
}
