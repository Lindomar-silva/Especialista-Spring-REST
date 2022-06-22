package com.algaworks.algafood.domain.repository;

import java.util.List;

import com.algaworks.algafood.domain.model.Cozinha;

public interface CozinhaRepository {

	List<Cozinha> listar();
	Cozinha buscarPorId(Long cozinhaId);
	Cozinha salvar(Cozinha cozinha);
	void remover(Long cozinhaId);
}
