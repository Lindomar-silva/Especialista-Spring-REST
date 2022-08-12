package com.algaworks.algafood.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;

@Service
public class CadastroCozinhaService {

	private static final String MSG_COZINHA_EM_USO 
	= "A cozinha '%s' não pode ser removida, pois está em uso!";

	@Autowired
	private CozinhaRepository repository;

	public List<Cozinha> findAll() {
		return repository.findAll();
	}

	public Cozinha salvar(Cozinha cozinha) {
		return repository.save(cozinha);
	}

	public void remover(Long cozinhaId) {
		Optional<Cozinha> cozinha = repository.findById(cozinhaId);

		try {
			repository.deleteById(cozinhaId);

		} catch (EmptyResultDataAccessException e) {
			throw new CozinhaNaoEncontradaException(cozinhaId);

		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(MSG_COZINHA_EM_USO, cozinha.get().getNome()));
		}
	}

	public Cozinha findByIdOrNotFound(Long cozinhaId) {
		return repository.findById(cozinhaId)
				.orElseThrow(() -> new CozinhaNaoEncontradaException(cozinhaId));
	}
}
