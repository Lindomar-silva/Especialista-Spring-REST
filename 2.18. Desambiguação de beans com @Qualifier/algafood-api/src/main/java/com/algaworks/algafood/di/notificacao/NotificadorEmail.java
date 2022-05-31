package com.algaworks.algafood.di.notificacao;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.di.modelo.Cliente;

@Qualifier(value = "email")
@Component
public class NotificadorEmail implements Notificador {

	@Override
	public void notificar(Cliente cliente, String msg) {

		System.out.printf("Notificação %s através do e-mail %s: %s\n", 
				cliente.getNome(), cliente.getEmail(), msg);
	}
}
