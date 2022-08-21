package com.algaworks.algafood.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;

@Service
public class EstadoService {

	private static final String MSG_ESTADO_EM_USO 
	= "Estado de código %d não pode ser removido, pois está em uso!";
	
	@Autowired
	private EstadoRepository repository;

	public List<Estado> findAll() {
		return repository.findAll();
	}

	@Transactional
	public Estado salvar(Estado estado) {
		return repository.save(estado);
	}

	@Transactional
	public void remover(Long estadoId) {
		try {
			repository.deleteById(estadoId);
	
		} catch (EmptyResultDataAccessException e) {
			throw new EstadoNaoEncontradoException(estadoId);
			
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format(MSG_ESTADO_EM_USO, estadoId));
		}
	}
	
	public Estado findByIdOrNotFound(Long estadoId) {
		return repository.findById(estadoId)
			.orElseThrow(() -> new EstadoNaoEncontradoException(estadoId));
	}

}