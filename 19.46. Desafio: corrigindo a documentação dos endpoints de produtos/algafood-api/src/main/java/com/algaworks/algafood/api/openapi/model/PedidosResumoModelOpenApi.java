package com.algaworks.algafood.api.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.algaworks.algafood.api.model.PedidoResumoDTO;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@ApiModel("PedidosModel")
@Getter
@Setter
public class PedidosResumoModelOpenApi {

	private PedidoEmbeddedModelOpenApi _embedded;
	private Links _links;
	private PageModelOpenApi page;

	@ApiModel("PedidosEmbeddedModel")
	@Data
	public class PedidoEmbeddedModelOpenApi {

		private List<PedidoResumoDTO> pedidos;
	}
}
