package com.algaworks.algafood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.v1.model.input.PedidoInput;
import com.algaworks.algafood.domain.model.Pedido;

@Component
public class PedidoDisassembler {

	@Autowired
	private ModelMapper mapper;

	public Pedido toDomainModel(PedidoInput pedidoInput) {
		return mapper.map(pedidoInput, Pedido.class);
	}

	public void copyCollectionModel(PedidoInput pedidoInput, Pedido pedido) {
		mapper.map(pedidoInput, pedido);
	}
}
