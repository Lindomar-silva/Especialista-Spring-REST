package com.algaworks.algafood.di.notificacao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.di.modelo.Cliente;

@TipoDoNotificador(NivelUrgencia.URGENTE)
@Component
public class NotificadorEmail implements Notificador {

	@Value("${notificador.email.host-server}")
	private String host;
	
	@Value("${notificador.email.porta-server}")
	private String porta;

	@Override
	public void notificar(Cliente cliente, String msg) {
		System.out.println("HOST: " + host);
		System.out.println("PORTA: " + porta);

		System.out.printf("Notificação %s através do e-mail %s: %s\n", 
				cliente.getNome(), cliente.getEmail(), msg);
	}
}
