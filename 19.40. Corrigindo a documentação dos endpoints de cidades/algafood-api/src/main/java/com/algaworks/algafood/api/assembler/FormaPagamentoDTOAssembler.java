package com.algaworks.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.AlgaLinks;
import com.algaworks.algafood.api.controller.FormaPagamentoController;
import com.algaworks.algafood.api.model.FormaPagamentoDTO;
import com.algaworks.algafood.domain.model.FormaPagamento;

@Component
public class FormaPagamentoDTOAssembler extends RepresentationModelAssemblerSupport<FormaPagamento, FormaPagamentoDTO> {

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private AlgaLinks algaLinks;

	public FormaPagamentoDTOAssembler() {
        super(FormaPagamentoController.class, FormaPagamentoDTO.class);
    }
	
	@Override
	public FormaPagamentoDTO toModel(FormaPagamento formaPagamento) {
		FormaPagamentoDTO formaPagamentoModel = 
                createModelWithId(formaPagamento.getId(), formaPagamento);
        
        mapper.map(formaPagamento, formaPagamentoModel);
        
        formaPagamentoModel.add(algaLinks.linkToFormasPagamento("formasPagamento"));
        
        return formaPagamentoModel;
	}

	@Override
    public CollectionModel<FormaPagamentoDTO> toCollectionModel(Iterable<? extends FormaPagamento> entities) {
        return super.toCollectionModel(entities)
            .add(algaLinks.linkToFormasPagamento());
    }
}
