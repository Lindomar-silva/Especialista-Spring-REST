package com.algaworks.algafood.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;

@Repository
public class CozinhaRepositoryImpl implements CozinhaRepository {

	@PersistenceContext
	private EntityManager manager;

	@Override
	public List<Cozinha> listar() {
		return manager.createQuery("From Cozinha", Cozinha.class).getResultList();
	}

	@Override
	public List<Cozinha> consultarPorNome(String nomeCozinha) {
		return manager.createQuery("From Cozinha WHERE nome LIKE :nome", Cozinha.class)
				.setParameter("nome", "%" + nomeCozinha + "%")
				.getResultList();
	}
	
	@Override
	public Cozinha buscarPorId(Long cozinhaId) {
		return manager.find(Cozinha.class, cozinhaId);
	}

	@Transactional
	@Override
	public Cozinha salvar(Cozinha cozinha) {
		return manager.merge(cozinha);
	}

	@Transactional
	@Override
	public void remover(Long cozinhaId) {
		Cozinha cozinha = buscarPorId(cozinhaId);

		if (cozinha == null) {
			throw new EmptyResultDataAccessException(1);
		}

		manager.remove(cozinha);
	}

}
