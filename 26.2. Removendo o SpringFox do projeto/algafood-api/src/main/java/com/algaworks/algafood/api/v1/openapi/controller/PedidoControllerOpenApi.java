package com.algaworks.algafood.api.v1.openapi.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

import com.algaworks.algafood.api.v1.model.PedidoDTO;
import com.algaworks.algafood.api.v1.model.PedidoResumoDTO;
import com.algaworks.algafood.api.v1.model.input.PedidoInput;
import com.algaworks.algafood.domain.filter.PedidoFilter;

public interface PedidoControllerOpenApi {

	PagedModel<PedidoResumoDTO> pesquisar(PedidoFilter filtro, Pageable pageable);

	PedidoDTO buscar(String pedidoUuId);

	PedidoDTO emitir(PedidoInput pedidoInput);

}
