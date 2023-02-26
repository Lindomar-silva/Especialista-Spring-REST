package com.algaworks.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.AlgaLinks;
import com.algaworks.algafood.api.controller.CidadeController;
import com.algaworks.algafood.api.model.CidadeDTO;
import com.algaworks.algafood.domain.model.Cidade;

@Component
public class CidadeDTOAssembler extends RepresentationModelAssemblerSupport<Cidade, CidadeDTO> {

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private AlgaLinks algaLinks;

	public CidadeDTOAssembler() {
		super(CidadeController.class, CidadeDTO.class);
	}
	
	@Override
	public CidadeDTO toModel(Cidade cidade) {

		CidadeDTO cidadeDTO = createModelWithId(cidade.getId(), cidade);
		mapper.map(cidade, cidadeDTO);
				
		cidadeDTO.add(algaLinks.linkToCidades("cidades"));
		
		cidadeDTO.getEstado().add(algaLinks.linkToEstado(cidade.getEstado().getId()));

		return cidadeDTO;
	}

	@Override
	public CollectionModel<CidadeDTO> toCollectionModel(Iterable<? extends Cidade> entities) {
		return super.toCollectionModel(entities)
				.add(algaLinks.linkToCidades());
	}
}
