package com.algaworks.algafood.api.openapi.model;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("Links")
public class LinksModelOpenApi {

	private LinkModel rel;

	@Getter
	@Setter
	@ApiModel("Link")
	private class LinkModel {

		private String rel;
		private boolean templated;
	}
}
