package com.algaworks.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.PedidoNaoEncontradoException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.PedidoRepository;
import com.algaworks.algafood.domain.repository.filter.PedidoFilter;
import com.algaworks.algafood.infrastructure.repository.spec.PedidoSpec;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository repository;
	
	@Autowired
	private CadastroRestauranteService restauranteService;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private CidadeService cidadeService;
	
	@Autowired
	private FormaPagamentoService pagamentoService;
	
	@Autowired
	private ProdutoService produtoService;
	
	public Page<Pedido> findAll(PedidoFilter filtro, Pageable pageable){
		return repository.findAll(PedidoSpec.usandoFiltro(filtro), pageable);
	}
	
	public Pedido findByIdOrNotFound(String pedidoUuId) {
		return repository.findByUuidCod(pedidoUuId)
				.orElseThrow(() -> new PedidoNaoEncontradoException(pedidoUuId));
	}
	
	@Transactional
	public Pedido emitir(Pedido pedido) {
		try {
			validarPedido(pedido);
			validarItens(pedido);

			pedido.setTaxaFrete(pedido.getRestaurante().getTaxaFrete());
			pedido.calcularValorTotal();

			return repository.save(pedido);

		} catch (EntidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	private void validarPedido(Pedido pedido) {
	    Usuario cliente = usuarioService.findByIdOrNotFound(pedido.getCliente().getId());
	    Restaurante restaurante = restauranteService.findByIdOrNotFound(pedido.getRestaurante().getId());
	    Cidade cidade = cidadeService.findByIdOrNotFound(pedido.getEnderecoEntrega().getCidade().getId());
	    FormaPagamento formaPagamento = pagamentoService.findByIdOrNotFound(pedido.getFormaPagamento().getId());

	    pedido.setCliente(cliente);
	    pedido.setRestaurante(restaurante);
	    pedido.getEnderecoEntrega().setCidade(cidade);
	    pedido.setFormaPagamento(formaPagamento);
	    
	    if (restaurante.naoAceitaFormaPagamento(formaPagamento)) {
	        throw new NegocioException(String.format("Forma de pagamento '%s' não é aceita por esse restaurante.",
	                formaPagamento.getDescricao()));
	    }
	}

	private void validarItens(Pedido pedido) {
	    pedido.getItens().forEach(item -> {
	        Produto produto = produtoService.findByIdOrNotFound(
	                pedido.getRestaurante().getId(), item.getProduto().getId());
	        
	        item.setPedido(pedido);
	        item.setProduto(produto);
	        item.setPrecoUnitario(produto.getPreco());
	    });
	}
}
