package com.algaworks.algafood.api.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.controller.UsuarioController;
import com.algaworks.algafood.api.controller.UsuarioGrupoController;
import com.algaworks.algafood.api.model.UsuarioDTO;
import com.algaworks.algafood.domain.model.Usuario;

@Component
public class UsuarioAssembler extends RepresentationModelAssemblerSupport<Usuario, UsuarioDTO> {

	@Autowired
	private ModelMapper mapper;

	public UsuarioAssembler() {
		super(UsuarioController.class, UsuarioDTO.class);
	}
	
	@Override
	public UsuarioDTO toModel(Usuario usuario) {
		UsuarioDTO usuarioDTO = createModelWithId(usuario.getId(), usuario);
		mapper.map(usuario, usuarioDTO);
		
//		usuarioDTO.add(linkTo(UsuarioController.class).withRel("usuarios"));
				
		usuarioDTO.add(linkTo(methodOn(UsuarioGrupoController.class)
				.listar(usuario.getId())).withRel("grupos-usuario"));
	        
		return usuarioDTO;
	}
	
	@Override
	public CollectionModel<UsuarioDTO> toCollectionModel(Iterable<? extends Usuario> entities) {
		return super.toCollectionModel(entities)
				.add(linkTo(UsuarioController.class).withSelfRel());
	}
	
	public UsuarioDTO toModelWithCollectionRel(Usuario usuario) {
		UsuarioDTO usuarioDTO = toModel(usuario);
		usuarioDTO.add(linkTo(UsuarioController.class).withRel("usuarios"));
		return usuarioDTO;
	}
}
