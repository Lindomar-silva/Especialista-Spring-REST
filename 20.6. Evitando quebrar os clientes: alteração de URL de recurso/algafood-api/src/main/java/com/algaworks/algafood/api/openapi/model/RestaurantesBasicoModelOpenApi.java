package com.algaworks.algafood.api.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.algaworks.algafood.api.model.RestauranteBasicoDTO;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel("RestaurantesModel")
@Data
public class RestaurantesBasicoModelOpenApi {

	private RestauranteEmbeddedModelOpenApi _embedded;
	private Links _links;

	@ApiModel("RestaurantesEmbeddedModel")
	@Data
	public class RestauranteEmbeddedModelOpenApi {

		private List<RestauranteBasicoDTO> restaurantes;
	}
}
