package com.algaworks.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;

@Service
public class EstadoService {

	@Autowired
	private EstadoRepository repository;

	public Estado salvar(Estado estado) {
		return repository.salvar(estado);
	}

	public Estado buscar(Long estadoId) {
		return repository.buscar(estadoId);
	}

	public void remover(Long estadoId) {
		try {
			repository.remover(estadoId);
	
		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(
					String.format("Não existe um cadastro de Estado com código %d", estadoId));
			
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format("Estado de código %d não pode ser removido, pois está em uso!", estadoId));
		}
	}
}