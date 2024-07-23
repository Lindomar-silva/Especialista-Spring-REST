package com.algaworks.algafood.api.v1.openapi.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.ServletWebRequest;

import com.algaworks.algafood.api.v1.model.FormaPagamentoDTO;
import com.algaworks.algafood.api.v1.model.input.FormaPagamentoInput;

public interface FormaPagamentoControllerOpenApi {

	ResponseEntity<CollectionModel<FormaPagamentoDTO>> listar(ServletWebRequest request);

	ResponseEntity<FormaPagamentoDTO> buscar(Long formaPagamentoId, ServletWebRequest request);

	FormaPagamentoDTO adicionar(FormaPagamentoInput formaPagamentoInput);

	FormaPagamentoDTO atualizar(Long formaPagamentoId, FormaPagamentoInput formaPagamentoInput);

	void remover(Long formaPagamentoId);
}
