package com.algaworks.algafood.api.assembler;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.PedidoResumoDTO;
import com.algaworks.algafood.domain.model.Pedido;

@Component
public class PedidoResumoDTOAssembler {

	@Autowired
	private ModelMapper mapper;
	
	public PedidoResumoDTO toDTO(Pedido pedido) {
		return mapper.map(pedido, PedidoResumoDTO.class);
	}
	
	public List<PedidoResumoDTO> toCollectionDTO(Collection<Pedido> pedidos){
		return pedidos.stream()
				.map(pedido -> toDTO(pedido))
				.collect(Collectors.toList());
	}
}
