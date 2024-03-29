package com.algaworks.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.AlgaLinks;
import com.algaworks.algafood.api.controller.RestauranteController;
import com.algaworks.algafood.api.model.RestauranteDTO;
import com.algaworks.algafood.domain.model.Restaurante;

@Component
public class RestauranteDTOAssembler extends RepresentationModelAssemblerSupport<Restaurante, RestauranteDTO> {

	public RestauranteDTOAssembler() {
		super(RestauranteController.class, RestauranteDTO.class);
	}

	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private AlgaLinks algaLinks;
	
	@Override
	public RestauranteDTO toModel(Restaurante restaurante) {
		RestauranteDTO restauranteModel = createModelWithId(restaurante.getId(), restaurante);
		mapper.map(restaurante, restauranteModel);

		restauranteModel.add(algaLinks.linkToRestaurantes("restaurantes"));

		restauranteModel.getCozinha().add(algaLinks.linkToCozinha(restaurante.getCozinha().getId()));

		restauranteModel.getEndereco().getCidade()
				.add(algaLinks.linkToCidade(restaurante.getEndereco().getCidade().getId()));

		restauranteModel.add(algaLinks.linkToRestauranteFormasPagamento(restaurante.getId(), "formas-pagamento"));

		restauranteModel.add(algaLinks.linkToResponsaveisRestaurante(restaurante.getId(), "responsaveis"));

		return restauranteModel;
	}

	@Override
	public CollectionModel<RestauranteDTO> toCollectionModel(Iterable<? extends Restaurante> entities) {
		return super.toCollectionModel(entities)
				.add(algaLinks.linkToRestaurantes());
	}

}
