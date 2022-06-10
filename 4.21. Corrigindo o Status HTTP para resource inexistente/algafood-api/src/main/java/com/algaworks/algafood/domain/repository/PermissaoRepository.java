package com.algaworks.algafood.domain.repository;

import java.util.List;

import com.algaworks.algafood.domain.model.Permissao;

public interface PermissaoRepository {

	List<Permissao> listarTodos();
	Permissao buscar(Long id);
	Permissao salvar(Permissao permissao);
	void remover(Permissao permissao);

}
