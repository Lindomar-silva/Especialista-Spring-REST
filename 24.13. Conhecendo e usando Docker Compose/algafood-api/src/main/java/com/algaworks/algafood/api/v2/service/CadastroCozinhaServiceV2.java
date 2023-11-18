package com.algaworks.algafood.api.v2.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.api.v2.assembler.CozinhaDTOAssemblerV2;
import com.algaworks.algafood.api.v2.model.CozinhaDTOV2;
import com.algaworks.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;

@Service
public class CadastroCozinhaServiceV2 {

	private static final String MSG_COZINHA_EM_USO 
	= "A cozinha '%s' não pode ser removida, pois está em uso!";

	@Autowired
	private CozinhaRepository repository;

	@Autowired
	private CozinhaDTOAssemblerV2 assembler;
	
	public Page<Cozinha> findAll(Pageable pageable) {
		return repository.findAll(pageable);
	}

	@Transactional
	public CozinhaDTOV2 salvar(Cozinha cozinha) {
		return assembler.toModel(repository.save(cozinha));
	}

	@Transactional 
	public void remover(Long cozinhaId) {
		Optional<Cozinha> cozinha = repository.findById(cozinhaId);

		try {
			repository.deleteById(cozinhaId);
			repository.flush(); //Evita erro de DataIntegrityViolationException fora do bloco try catch
			
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