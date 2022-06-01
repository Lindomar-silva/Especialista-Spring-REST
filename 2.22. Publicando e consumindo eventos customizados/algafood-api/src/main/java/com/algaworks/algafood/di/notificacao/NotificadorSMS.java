package com.algaworks.algafood.di.notificacao;

import org.springframework.stereotype.Component;

import com.algaworks.algafood.di.modelo.Cliente;

@TipoDoNotificador(NivelUrgencia.NORMAL)
@Component
public class NotificadorSMS implements Notificador {

	@Override
	public void notificar(Cliente cliente, String msg) {

		System.out.printf("Notificando %s por SMS através do telefone %s: %s\n", cliente.getNome(),
				cliente.getTelefone(), msg);
	}
}
