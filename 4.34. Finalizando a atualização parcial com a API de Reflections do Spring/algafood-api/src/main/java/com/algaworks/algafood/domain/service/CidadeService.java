package com.algaworks.algafood.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.CidadeRepository;

@Service
public class CidadeService {

	@Autowired
	private CidadeRepository repository;

	@Autowired
	private EstadoService estadoService;

	public List<Cidade> listar() {
		return repository.listar();
	}

	public Cidade buscar(Long cidadeId) {
		return repository.buscar(cidadeId);
	}

	public Cidade salvar(Cidade cidade) {
		Long estadoId = cidade.getEstado().getId();
		Estado estado = estadoService.buscar(estadoId);

//		System.out.println(cidade.getEstado().getId());

		if (estado == null) {
			throw new EntidadeNaoEncontradaException(
					String.format("Não existe um Estado com o código %d cadastrado!", estadoId));
		}

//		cidade.setEstado(estado);
		return repository.salvar(cidade);
	}

	public void remover(Long cidadeId) {
		try {
			repository.remover(cidadeId);

		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(
					String.format("Não existe um cadastro de Cidade com código %d", cidadeId));
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format("Cidade de código %d não pode ser removida, pois está em uso", cidadeId));
		}
	}
}
