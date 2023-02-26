package com.algaworks.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.AlgaLinks;
import com.algaworks.algafood.api.controller.RestauranteController;
import com.algaworks.algafood.api.model.RestauranteApenasNomeDTO;
import com.algaworks.algafood.domain.model.Restaurante;

@Component
public class RestauranteApenasNomeDTOAssembler
		extends RepresentationModelAssemblerSupport<Restaurante, RestauranteApenasNomeDTO> {

	@Autowired
    private ModelMapper modelMapper;
    
    @Autowired
    private AlgaLinks algaLinks;
    
    public RestauranteApenasNomeDTOAssembler() {
        super(RestauranteController.class, RestauranteApenasNomeDTO.class);
    }
    
    @Override
    public RestauranteApenasNomeDTO toModel(Restaurante restaurante) {
    	RestauranteApenasNomeDTO restauranteModel = createModelWithId(
                restaurante.getId(), restaurante);
        
        modelMapper.map(restaurante, restauranteModel);
        
        restauranteModel.add(algaLinks.linkToRestaurantes("restaurantes"));
        
        return restauranteModel;
    }
    
    @Override
    public CollectionModel<RestauranteApenasNomeDTO> toCollectionModel(Iterable<? extends Restaurante> entities) {
        return super.toCollectionModel(entities)
                .add(algaLinks.linkToRestaurantes());
    }   
}
