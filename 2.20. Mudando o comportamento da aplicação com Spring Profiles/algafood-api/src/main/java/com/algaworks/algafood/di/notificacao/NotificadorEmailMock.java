package com.algaworks.algafood.di.notificacao;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.di.modelo.Cliente;

@Profile("dev")
@TipoDoNotificador(NivelUrgencia.URGENTE)
@Component
public class NotificadorEmailMock implements Notificador {

	public NotificadorEmailMock() {
		System.out.println("Notificador MOCK!");
	}
	@Override
	public void notificar(Cliente cliente, String msg) {
		System.out.printf("MOCK: Notificação %s através do e-mail %s: %s\n", 
				cliente.getNome(), cliente.getEmail(), msg);
	}
}
