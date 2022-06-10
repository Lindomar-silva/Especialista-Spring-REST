package com.algaworks.algafood.domain.repository;

import java.util.List;

import com.algaworks.algafood.domain.model.Cidade;

public interface CidadeRepository {

	List<Cidade> listarTodos();
	Cidade buscar(Long id);
	Cidade salvar(Cidade estado);
	void remover(Cidade estado);
}
