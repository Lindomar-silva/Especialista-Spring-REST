package com.algaworks.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.input.UsuarioInput;
import com.algaworks.algafood.domain.model.Usuario;

@Component
public class UsuarioDisassembler {

	@Autowired
	private ModelMapper mapper;

	public Usuario toDomainModel(UsuarioInput usuarioInput) {
		return mapper.map(usuarioInput, Usuario.class);
	}

	public void copyDomainModel(UsuarioInput usuarioInput, Usuario usuario) {
		mapper.map(usuarioInput, usuario);
	}
}
