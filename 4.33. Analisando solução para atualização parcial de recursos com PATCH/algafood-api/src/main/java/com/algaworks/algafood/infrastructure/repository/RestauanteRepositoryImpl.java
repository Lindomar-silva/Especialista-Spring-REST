package com.algaworks.algafood.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;

@Component
public class RestauanteRepositoryImpl implements RestauranteRepository {

	@PersistenceContext
	EntityManager maneger;

	@Override
	public List<Restaurante> listar() {
		return maneger.createQuery("From Restaurante", Restaurante.class).getResultList();
	}

	@Override
	public Restaurante buscar(Long restauranteId) {
		return maneger.find(Restaurante.class, restauranteId);
	}

	@Transactional
	@Override
	public Restaurante salvar(Restaurante restaurante) {
		return maneger.merge(restaurante);
	}

	@Transactional
	@Override
	public void remover(Restaurante restaurante) {
		restaurante = buscar(restaurante.getId());
		maneger.remove(restaurante);
	}
}
