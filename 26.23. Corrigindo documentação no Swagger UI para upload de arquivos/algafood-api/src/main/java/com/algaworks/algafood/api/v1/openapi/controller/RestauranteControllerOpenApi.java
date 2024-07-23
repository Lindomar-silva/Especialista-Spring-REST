package com.algaworks.algafood.api.v1.openapi.controller;

import java.util.List;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import com.algaworks.algafood.api.v1.model.RestauranteApenasNomeDTO;
import com.algaworks.algafood.api.v1.model.RestauranteBasicoDTO;
import com.algaworks.algafood.api.v1.model.RestauranteDTO;
import com.algaworks.algafood.api.v1.model.input.RestauranteInput;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@SecurityRequirement(name = "security_auth")
public interface RestauranteControllerOpenApi {

	@Operation(parameters = {
			@Parameter(name = "projecao", description = "Nome da projeção", 
					example = "resumo", in = ParameterIn.QUERY, required = false) 
	})
	CollectionModel<RestauranteBasicoDTO> listar();

	CollectionModel<RestauranteApenasNomeDTO> listarResumo();

	RestauranteDTO buscar(Long restauranteId);

	RestauranteDTO adicionar(RestauranteInput restauranteInput);

	RestauranteDTO atualizar(Long restauranteId, RestauranteInput restauranteInput);

	ResponseEntity<Void> ativar(Long restauranteId);

	ResponseEntity<Void> inativar(Long restauranteId);

	void ativarMultiplos(List<Long> restauranteIds);

	void inativarMultiplos(List<Long> restauranteIds);

	ResponseEntity<Void> fechado(Long restauranteId);

	ResponseEntity<Void> aberto(Long restauranteId);
}
