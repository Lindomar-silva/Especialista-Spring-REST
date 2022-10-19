package com.algaworks.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.service.EnvioEmailService.Mensagem;

@Service
public class FluxoPedidoService {

	@Autowired
	private PedidoService pedidoService;

	@Autowired
	private EnvioEmailService emailService;
	
	@Transactional
	public void confirmar(String pedidoId) {
		Pedido pedido = pedidoService.findByIdOrNotFound(pedidoId);
		pedido.confirmar();
		
		var mensagem = Mensagem.builder()
				.assunto(pedido.getRestaurante().getNome() + " - Pedido confirmado")
				.corpo("O pedido de código <strong>" + pedido.getUuidCod() + "</strong> foi confirmado!")
				.destinatario(pedido.getCliente().getEmail())
				.build();

		emailService.enviar(mensagem);
	}

	@Transactional
	public void cancelar(String pedidoId) {
		Pedido pedido = pedidoService.findByIdOrNotFound(pedidoId);
		pedido.cancelar();
	}

	@Transactional
	public void entregar(String pedidoId) {
		Pedido pedido = pedidoService.findByIdOrNotFound(pedidoId);
		pedido.entregar();
	}
}
