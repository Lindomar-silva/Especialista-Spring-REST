package com.algaworks.algafood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.api.v1.controller.RestauranteProdutoFotoController;
import com.algaworks.algafood.api.v1.model.FotoProdutoDTO;
import com.algaworks.algafood.domain.model.FotoProduto;

@Component
public class FotoProdutoAssembler extends RepresentationModelAssemblerSupport<FotoProduto, FotoProdutoDTO> {

	@Autowired
	private ModelMapper mapper;

	@Autowired
    private AlgaLinks algaLinks;
	
	public FotoProdutoAssembler() {
	        super(RestauranteProdutoFotoController.class, FotoProdutoDTO.class);
	    }

	public FotoProdutoDTO toModel(FotoProduto foto) {
		FotoProdutoDTO fotoProdutoModel = mapper.map(foto, FotoProdutoDTO.class);
        
        fotoProdutoModel.add(algaLinks.linkToFotoProduto(
                foto.getRestauranteId(), foto.getProduto().getId()));
        
        fotoProdutoModel.add(algaLinks.linkToProduto(
                foto.getRestauranteId(), foto.getProduto().getId(), "produto"));
        
        return fotoProdutoModel;
	}
}
