package com.algaworks.algafood.api.v1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.v1.assembler.PermissaoAssembler;
import com.algaworks.algafood.api.v1.model.PermissaoDTO;
import com.algaworks.algafood.api.v1.openapi.controller.PermissaoControllerOpenApi;
import com.algaworks.algafood.domain.model.Permissao;
import com.algaworks.algafood.domain.repository.PermissaoRepository;


@RestController
@RequestMapping(path = "/permissoes", produces = MediaType.APPLICATION_JSON_VALUE)
public class PermissaoController implements PermissaoControllerOpenApi {

	@Autowired
    private PermissaoRepository permissaoRepository;
    
    @Autowired
    private PermissaoAssembler permissaoModelAssembler;
    
    @Override
    @GetMapping
    public CollectionModel<PermissaoDTO> listar() {
        List<Permissao> todasPermissoes = permissaoRepository.findAll();
        
        return permissaoModelAssembler.toCollectionModel(todasPermissoes);
    }   
}
