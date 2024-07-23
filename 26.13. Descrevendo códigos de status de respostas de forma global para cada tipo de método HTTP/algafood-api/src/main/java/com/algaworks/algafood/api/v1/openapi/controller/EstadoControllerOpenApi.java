package com.algaworks.algafood.api.v1.openapi.controller;

import org.springframework.hateoas.CollectionModel;

import com.algaworks.algafood.api.v1.model.EstadoDTO;
import com.algaworks.algafood.api.v1.model.input.EstadoInput;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@SecurityRequirement(name = "security_auth")
public interface EstadoControllerOpenApi {

	CollectionModel<EstadoDTO> listar();

	EstadoDTO buscar(Long estadoId);

	EstadoDTO adicionar(EstadoInput estadoInput);

	EstadoDTO atualizar(Long estadoId, EstadoInput estadoInput);

	void remover(Long estadoId);
}
