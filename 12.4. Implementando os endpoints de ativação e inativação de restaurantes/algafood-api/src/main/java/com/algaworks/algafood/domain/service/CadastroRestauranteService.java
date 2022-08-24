package com.algaworks.algafood.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.api.assembler.RestauranteDTOAssembler;
import com.algaworks.algafood.api.model.RestauranteDTO;
import com.algaworks.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.RestauranteNaoEncontradoException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;

@Service
public class CadastroRestauranteService {

	@Autowired
	private RestauranteDTOAssembler dtoAssembler;
	@Autowired
	private RestauranteRepository restauranteRepo;

	@Autowired
	private CadastroCozinhaService cozinhaService;

	public List<Restaurante> findAll() {
		return restauranteRepo.findAll();
	}

	@Transactional
	public RestauranteDTO salvar(Restaurante restaurante) {
		try {
			Long cozinhaId = restaurante.getCozinha().getId();

			Cozinha cozinha = cozinhaService.findByIdOrNotFound(cozinhaId);
			restaurante.setCozinha(cozinha);

			return dtoAssembler.toDTO(restauranteRepo.save(restaurante));

		} catch (CozinhaNaoEncontradaException e) {
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
	public void inaativar(Long restauranteId) {
		Restaurante restaurante = findByIdOrNotFound(restauranteId);

		//Istancia gerenciada (não precisa fazer save)
		restaurante.inativar();
	}
	
	public Restaurante findByIdOrNotFound(Long restauranteId) {
		return restauranteRepo.findById(restauranteId)
				.orElseThrow(() -> new RestauranteNaoEncontradoException(restauranteId));
	}

}
