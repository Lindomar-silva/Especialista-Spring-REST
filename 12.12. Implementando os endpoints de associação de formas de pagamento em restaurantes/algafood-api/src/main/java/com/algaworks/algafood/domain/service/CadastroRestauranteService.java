package com.algaworks.algafood.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.api.assembler.RestauranteDTOAssembler;
import com.algaworks.algafood.api.model.RestauranteDTO;
import com.algaworks.algafood.domain.exception.CidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.RestauranteNaoEncontradoException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;

@Service
public class CadastroRestauranteService {

	@Autowired
	private RestauranteDTOAssembler dtoAssembler;
	
	@Autowired
	private RestauranteRepository restauranteRepo;

	@Autowired
	private FormaPagamentoService pagamentoService;
	
	@Autowired
	private CadastroCozinhaService cozinhaService;
	
	@Autowired
	private CidadeService cidadeService;

	public List<Restaurante> findAll() {
		return restauranteRepo.findAll();
	}

	@Transactional
	public RestauranteDTO salvar(Restaurante restaurante) {
		try {
			Long cozinhaId = restaurante.getCozinha().getId();
			Long cidadeId = restaurante.getEndereco().getCidade().getId();
			
			Cozinha cozinha = cozinhaService.findByIdOrNotFound(cozinhaId);
			Cidade cidade = cidadeService.findByIdOrNotFound(cidadeId);
			
			restaurante.setCozinha(cozinha);
			restaurante.getEndereco().setCidade(cidade);
			
			return dtoAssembler.toDTO(restauranteRepo.save(restaurante));

		} catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	@Transactional
	public void ativar(Long restauranteId) {
		Restaurante restaurante = findByIdOrNotFound(restauranteId);

		//Istancia gerenciada (não precisa fazer save)
		restaurante.ativar();
	}

	@Transactional
	public void inativar(Long restauranteId) {
		Restaurante restaurante = findByIdOrNotFound(restauranteId);

		//Istancia gerenciada (não precisa fazer save)
		restaurante.inativar();
	}
	
	@Transactional
	public void associarFormaPagamento(Long restauranteId, Long formaPagamentoId) {
		Restaurante restaurante = findByIdOrNotFound(restauranteId);
		
		if(restaurante.notExistFormaPagamento(formaPagamentoId)) {
			FormaPagamento formaPagamento = pagamentoService.findByIdOrNotFound(formaPagamentoId);			
			
			//Istancia gerenciada (@Transactional) não precisa fazer save
			restaurante.adicionarFormaPagamento(formaPagamento);
		}	
	}
	
	@Transactional
	public void desassociarFormaPagamento(Long restauranteId, Long formaPagamentoId) {
		Restaurante restaurante = findByIdOrNotFound(restauranteId);

		if (restaurante.notExistFormaPagamento(formaPagamentoId)) {
			throw new NegocioException(String
					.format("A forma de pagamento do código '%d' não esta associado ao restaurante", formaPagamentoId));
		}

		FormaPagamento formaPagamento = pagamentoService.findByIdOrNotFound(formaPagamentoId);

		// Istancia gerenciada (@Transactional) não precisa fazer save
		restaurante.removerFormaPagamento(formaPagamento);
	}

	public Restaurante findByIdOrNotFound(Long restauranteId) {
		return restauranteRepo.findById(restauranteId)
				.orElseThrow(() -> new RestauranteNaoEncontradoException(restauranteId));
	}
}
