package com.algaworks.algafood.domain.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.api.assembler.CozinhaDTOAssembler;
import com.algaworks.algafood.api.model.CozinhaDTO;
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

	@Autowired
	private CozinhaDTOAssembler assembler;
	
	public Page<Cozinha> findAll(Pageable pageable) {
		return repository.findAll(pageable);
	}

	@Transactional
	public CozinhaDTO salvar(Cozinha cozinha) {
		return assembler.toDTO(repository.save(cozinha));
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
