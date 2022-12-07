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
import com.algaworks.algafood.domain.model.Usuario;
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
	private UsuarioService usuarioService;
	
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
	public void ativarMultiplos(List<Long> restauranteIds) {
		try {
			restauranteIds.forEach(this::ativar);

		} catch (RestauranteNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	@Transactional
	public void inativarMultiplos(List<Long> restauranteIds) {
		try {
//			restauranteIds.forEach(this::inativar);
			
			System.err.println(restauranteRepo.findAllById(restauranteIds));

		} catch (RestauranteNaoEncontradoException e) {
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
	
//	@Transactional
//	public void fechar(Long restauranteId) {
//		Restaurante restaurante = findByIdOrNotFound(restauranteId);
//		restaurante.fechar();
//	}
//
//	@Transactional
//	public void abrir(Long restauranteId) {
//		Restaurante restaurante = findByIdOrNotFound(restauranteId);
//		restaurante.abrir();
//	}
	
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
	
	@Transactional
	public void associarResponsavel(Long restauranteId, Long responsavelId) {
		Restaurante restaurante = findByIdOrNotFound(restauranteId);

		if (restaurante.notExistResposavel(responsavelId)) {
			Usuario resposnavel = usuarioService.findByIdOrNotFound(responsavelId);
			restaurante.adicionarResposavel(resposnavel);
		}
	}

	@Transactional
	public void desassociarResponsavel(Long restauranteId, Long responsavelId) {
		Restaurante restaurante = findByIdOrNotFound(restauranteId);

		if (restaurante.notExistResposavel(responsavelId)) {
			throw new NegocioException(String.format("O Responsável de código '%d' não esta associado ao Restaurante '%s'", 
					responsavelId, restaurante.getNome()));
		}
		
		Usuario resposnavel = usuarioService.findByIdOrNotFound(responsavelId);
		restaurante.removerResposavel(resposnavel);
	}
	
	public Restaurante findByIdOrNotFound(Long restauranteId) {
		return restauranteRepo.findById(restauranteId)
				.orElseThrow(() -> new RestauranteNaoEncontradoException(restauranteId));
	}
	
	@Transactional
	public int aberto(Long restauranteId) {
		existByIdOrNotFound(restauranteId);
		return restauranteRepo.updateStatusAberto(restauranteId, true);
	}

	@Transactional
	public int fechado(Long restauranteId) {
		existByIdOrNotFound(restauranteId);
		return restauranteRepo.updateStatusAberto(restauranteId, false);
	}
	
	public boolean existByIdOrNotFound(Long restauranteId) {
		if (countById(restauranteId) > 0)
			return true;

		throw new RestauranteNaoEncontradoException(restauranteId);
	}

	private int countById(Long restauranteId) {
		return restauranteRepo.countById(restauranteId);
	}
}
