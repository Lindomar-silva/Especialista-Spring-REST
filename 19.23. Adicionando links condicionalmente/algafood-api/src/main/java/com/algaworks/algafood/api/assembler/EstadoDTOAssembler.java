package com.algaworks.algafood.api.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.AlgaLinks;
import com.algaworks.algafood.api.controller.EstadoController;
import com.algaworks.algafood.api.model.EstadoDTO;
import com.algaworks.algafood.domain.model.Estado;

@Component
public class EstadoDTOAssembler extends RepresentationModelAssemblerSupport<Estado, EstadoDTO> {

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private AlgaLinks algaLinks;
	
	public EstadoDTOAssembler() {
		super(EstadoController.class, EstadoDTO.class);
	}
	
	@Override
	public EstadoDTO toModel(Estado estado) {
		EstadoDTO estadoDTO = createModelWithId(estado.getId(), estado);
		mapper.map(estado, estadoDTO);
		
		return estadoDTO;
	}
	
	@Override
	public CollectionModel<EstadoDTO> toCollectionModel(Iterable<? extends Estado> entities) {
		return super.toCollectionModel(entities)
				.add(linkTo(EstadoController.class).withSelfRel());
	}
	
	public EstadoDTO toModelWithCollectionRel(Estado estado) {
		EstadoDTO estadoDTO = toModel(estado);
				
		estadoDTO.add(algaLinks.linkToEstados("estados"));
		
		return estadoDTO;
	}
}
