package com.algaworks.algafood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.api.v1.controller.PedidoController;
import com.algaworks.algafood.api.v1.model.PedidoDTO;
import com.algaworks.algafood.domain.model.Pedido;

@Component
public class PedidoDTOAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoDTO> {

	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private AlgaLinks algaLinks;
	
	public PedidoDTOAssembler() {
		super(PedidoController.class, PedidoDTO.class);
	}
	
	@Override
	public PedidoDTO toModel(Pedido pedido) {
		PedidoDTO pedidoDTO = createModelWithId(pedido.getUuidCod(), pedido);
		mapper.map(pedido, pedidoDTO);
		
		pedidoDTO.add(algaLinks.linkToPedidos("pedidos"));
		
		if(pedido.podeSerConfirmado()) {
			pedidoDTO.add(algaLinks.linkToConfirmacaoPedido(pedido.getUuidCod(), "confirmar"));		
		}
		
		if(pedido.podeSerCancelado()) {
			pedidoDTO.add(algaLinks.linkToCancelamentoPedido(pedido.getUuidCod(), "cancelar"));
		}
		
		if(pedido.podeSerEntregue()) {
			pedidoDTO.add(algaLinks.linkToEntregaPedido(pedido.getUuidCod(), "entregar"));
		}
		
		pedidoDTO.getRestaurante().add(algaLinks.linkToRestaurante(pedido.getRestaurante().getId()));
		
		pedidoDTO.getCliente().add(algaLinks.linkToUsuario(pedido.getCliente().getId()));
		
		pedidoDTO.getFormaPagamento().add(algaLinks.linkToFormaPagamento(pedido.getFormaPagamento().getId()));
		
		pedidoDTO.getEnderecoEntrega().getCidade()
				.add(algaLinks.linkToCidade(pedido.getEnderecoEntrega().getCidade().getId()));
        
		pedidoDTO.getItens().forEach(item -> {
			item.add(algaLinks.linkToProduto(pedido.getRestaurante().getId(), item.getProdutoId(), "produto"));
        });
        
		return pedidoDTO;
	}
}
