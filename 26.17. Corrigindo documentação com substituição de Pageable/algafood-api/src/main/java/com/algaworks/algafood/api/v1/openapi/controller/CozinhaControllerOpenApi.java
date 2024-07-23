package com.algaworks.algafood.api.v1.openapi.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

import com.algaworks.algafood.api.v1.model.CozinhaDTO;
import com.algaworks.algafood.api.v1.model.input.CozinhaInput;
import com.algaworks.algafood.core.springdoc.PageableParamiter;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@SecurityRequirement(name = "security_auth")
public interface CozinhaControllerOpenApi {

	@PageableParamiter
	PagedModel<CozinhaDTO> listar(@Parameter(hidden = true) Pageable pageable);

	CozinhaDTO buscar(Long cozinhaId);

	CozinhaDTO adicionar(CozinhaInput cozinhaInput);

	CozinhaDTO atualizar(Long cozinhaId, CozinhaInput cozinhaInput);

	void remover(Long cozinhaId);
}
