package com.algaworks.algafood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.api.v1.controller.PedidoController;
import com.algaworks.algafood.api.v1.model.PedidoDTO;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.domain.model.Pedido;

@Component
public class PedidoDTOAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoDTO> {

	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private AlgaLinks algaLinks;
	
	@Autowired
	private AlgaSecurity algaSecurity;
	
	public PedidoDTOAssembler() {
		super(PedidoController.class, PedidoDTO.class);
	}
	
	@Override
	public PedidoDTO toModel(Pedido pedido) {
		PedidoDTO pedidoDTO = createModelWithId(pedido.getUuidCod(), pedido);
		mapper.map(pedido, pedidoDTO);
		
		// Não é necessário usar o método algaSecurity.podePesquisarPedidos(clienteId, restauranteId) aqui
		// Porque na geração do link, não tem o id do cliente e do restaurante
		// Basta saber apenas se a requisição está autenticada e tem o escopo de leitura
		if (algaSecurity.podePesquisarPedidos()) {
			pedidoDTO.add(algaLinks.linkToPedidos("pedidos"));
		}
		
		if(algaSecurity.podeGerenciarPedido(pedido.getUuidCod())) {
			if(pedido.podeSerConfirmado()) {
				pedidoDTO.add(algaLinks.linkToConfirmacaoPedido(pedido.getUuidCod(), "confirmar"));		
			}
			
			if(pedido.podeSerCancelado()) {
				pedidoDTO.add(algaLinks.linkToCancelamentoPedido(pedido.getUuidCod(), "cancelar"));
			}
			
			if(pedido.podeSerEntregue()) {
				pedidoDTO.add(algaLinks.linkToEntregaPedido(pedido.getUuidCod(), "entregar"));
			}
		}
		
		if (algaSecurity.podeConsultarRestaurantes()) {
			pedidoDTO.getRestaurante().add(algaLinks.linkToRestaurante(pedido.getRestaurante().getId()));
		}
		
		if (algaSecurity.podeConsultarUsuariosGruposPermissoes()) {
			pedidoDTO.getCliente().add(algaLinks.linkToUsuario(pedido.getCliente().getId()));
		}
		
		if (algaSecurity.podeConsultarFormasPagamento()) {
			pedidoDTO.getFormaPagamento().add(algaLinks.linkToFormaPagamento(pedido.getFormaPagamento().getId()));
		}
		
		if (algaSecurity.podeConsultarCidades()) {
			pedidoDTO.getEnderecoEntrega().getCidade()
					.add(algaLinks.linkToCidade(pedido.getEnderecoEntrega().getCidade().getId()));
		}
		
		// Quem pode consultar restaurantes, também pode consultar os produtos dos restaurantes
		if (algaSecurity.podeConsultarRestaurantes()) {
			pedidoDTO.getItens().forEach(item -> {
				item.add(algaLinks.linkToProduto(pedido.getRestaurante().getId(), item.getProdutoId(), "produto"));
	        });
		}
		
		return pedidoDTO;
	}
}
