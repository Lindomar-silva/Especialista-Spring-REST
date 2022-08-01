package com.algaworks.algafood.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;

@Service
public class CadastroRestauranteService {

	private static final String MSG_RESTAURANTE_NAO_ENCONTRADO 
	= "Não existe cadastro de restaurante com o código %d ";
	
	@Autowired
	private RestauranteRepository restauranteRepo;

	@Autowired
	private CadastroCozinhaService cozinhaService;

	public List<Restaurante> findAll() {
		return restauranteRepo.findAll();
	}
	
	public Restaurante salvar(Restaurante restaurante) {
		try {
			Long cozinhaId = restaurante.getCozinha().getId();

			Cozinha cozinha = cozinhaService.findByIdOrNotFound(cozinhaId);
			restaurante.setCozinha(cozinha);

			return restauranteRepo.save(restaurante);

		} catch (EntidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}
	}
	
	public Restaurante findByIdOrNotFound(Long restauranteId) {
		return restauranteRepo.findById(restauranteId)
			.orElseThrow(() -> new EntidadeNaoEncontradaException(
					String.format(MSG_RESTAURANTE_NAO_ENCONTRADO, restauranteId)));
	}


}
