package com.algaworks.algafood.api.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.controller.CidadeController;
import com.algaworks.algafood.api.controller.EstadoController;
import com.algaworks.algafood.api.model.CidadeDTO;
import com.algaworks.algafood.domain.model.Cidade;

@Component
public class CidadeDTOAssembler extends RepresentationModelAssemblerSupport<Cidade, CidadeDTO> {

	@Autowired
	private ModelMapper mapper;

	public CidadeDTOAssembler() {
		super(CidadeController.class, CidadeDTO.class);
	}
	
	@Override
	public CidadeDTO toModel(Cidade cidade) {

		CidadeDTO cidadeDTO = createModelWithId(cidade.getId(), cidade);
		mapper.map(cidade, cidadeDTO);
				
		cidadeDTO.add(linkTo(methodOn(CidadeController.class)
				.listar()).withRel("cidades"));
		
		cidadeDTO.add(linkTo(methodOn(EstadoController.class)
				.buscar(cidadeDTO.getEstado().getId())).withSelfRel());
		
		return cidadeDTO;
	}

	@Override
	public CollectionModel<CidadeDTO> toCollectionModel(Iterable<? extends Cidade> entities) {
		return super.toCollectionModel(entities)
				.add(linkTo(CidadeController.class).withSelfRel());
	}
}
