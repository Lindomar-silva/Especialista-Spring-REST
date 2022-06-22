package com.algaworks.algafood.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;

@Component
public class EstadoRepositoryImpl implements EstadoRepository {

	@PersistenceContext
	EntityManager manager;

	@Override
	public List<Estado> listar() {
		return manager.createQuery("From Estado", Estado.class).getResultList();
	}

	@Override
	public Estado buscar(Long estadoId) {
		return manager.find(Estado.class, estadoId);
	}

	@Transactional
	@Override
	public Estado salvar(Estado estado) {
		return manager.merge(estado);
	}

	@Transactional
	@Override
	public void remover(Long estadoId) {
		Estado estado = buscar(estadoId);

		if (estado == null) {
			throw new EmptyResultDataAccessException(1);
		}

		manager.remove(estado);
	}

}
