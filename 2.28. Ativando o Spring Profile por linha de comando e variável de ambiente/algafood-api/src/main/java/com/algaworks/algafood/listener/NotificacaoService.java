package com.algaworks.algafood.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.di.notificacao.NivelUrgencia;
import com.algaworks.algafood.di.notificacao.Notificador;
import com.algaworks.algafood.di.notificacao.TipoDoNotificador;
import com.algaworks.algafood.di.service.ClienteAtivadoEvent;

@Component
public class NotificacaoService {

	@TipoDoNotificador(NivelUrgencia.URGENTE)
	@Autowired
	private Notificador notificador;

	@Order(1)
	@EventListener
	public void ClienteAtivadoListener(ClienteAtivadoEvent event) {
		notificador.notificar(event.getCliente(), "Seu cadastro no sistema está ativo");
	}
}
