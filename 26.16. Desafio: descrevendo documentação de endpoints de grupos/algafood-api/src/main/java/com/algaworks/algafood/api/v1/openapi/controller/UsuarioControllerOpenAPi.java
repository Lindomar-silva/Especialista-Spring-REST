package com.algaworks.algafood.api.v1.openapi.controller;

import org.springframework.hateoas.CollectionModel;

import com.algaworks.algafood.api.v1.model.UsuarioDTO;
import com.algaworks.algafood.api.v1.model.input.SenhaInput;
import com.algaworks.algafood.api.v1.model.input.UsuarioComSenhaInput;
import com.algaworks.algafood.api.v1.model.input.UsuarioInput;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@SecurityRequirement(name = "security_auth")
public interface UsuarioControllerOpenAPi {

	CollectionModel<UsuarioDTO> listar();

	UsuarioDTO buscar(Long usuarioId);

	UsuarioDTO adicionar(UsuarioComSenhaInput usuarioComSenhaInput);

	UsuarioDTO atualizar(Long usuarioId, UsuarioInput usuarioInput);

	void remover(Long usuarioId);

	void AlterarSenha(Long usuarioId, SenhaInput senhaInput);
}
