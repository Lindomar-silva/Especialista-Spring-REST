package com.algaworks.algafood.api.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.algaworks.algafood.api.model.EstadoDTO;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@ApiModel("EstadosModel")
@Getter
@Setter
public class EstadosModelOpenApi {

	private EstadoEmbeddedModelOpenApi _embedded;
	private Links _links;
	
	@ApiModel("EstadosEmbeddedModel")
	@Data
	public class EstadoEmbeddedModelOpenApi {

		private List<EstadoDTO> estados;
	}
}
