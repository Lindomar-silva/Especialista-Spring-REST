package com.algaworks.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.AlgaLinks;
import com.algaworks.algafood.api.controller.PedidoController;
import com.algaworks.algafood.api.model.PedidoResumoDTO;
import com.algaworks.algafood.domain.model.Pedido;

@Component
public class PedidoResumoDTOAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoResumoDTO> {

	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private AlgaLinks algaLinks;
	
	public PedidoResumoDTOAssembler() {
		super(PedidoController.class, PedidoResumoDTO.class);
	}
	
	@Override
	public PedidoResumoDTO toModel(Pedido pedido) {
		PedidoResumoDTO pedidoResumoDTO = createModelWithId(pedido.getUuidCod(), pedido);
		mapper.map(pedido, pedidoResumoDTO);
		
		pedidoResumoDTO.add(algaLinks.linkToPedidos());

		pedidoResumoDTO.getRestaurante().add(algaLinks.linkToRestaurante(pedido.getRestaurante().getId()));
				
		pedidoResumoDTO.getCliente().add(algaLinks.linkToUsuario(pedido.getCliente().getId()));
		
		return pedidoResumoDTO;
	}
}
