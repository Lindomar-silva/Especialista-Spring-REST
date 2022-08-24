package com.algaworks.algafood.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.api.assembler.UsuarioAssembler;
import com.algaworks.algafood.api.model.UsuarioDTO;
import com.algaworks.algafood.api.model.input.SenhaInput;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.UsuarioNaoEncontradoException;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.UsuarioRepository;

@Service
public class UsuarioService {

	private static final String MSG_USUARIO_EM_USO 
	= "Usuário de código %d não pode ser removido, pois está em uso!";
	
	@Autowired
	private UsuarioRepository repository;
	
	@Autowired
	private UsuarioAssembler assembler;

	public List<Usuario> findAll() {
		return repository.findAll();
	}
	
	public Usuario findByIdOrNotFound(Long usuarioId) {
		return repository.findById(usuarioId)
				.orElseThrow(() -> new UsuarioNaoEncontradoException(usuarioId));
	}
	
	@Transactional
	public UsuarioDTO salvar(Usuario usuario) {
		return assembler.toDTO(repository.save(usuario));
	}
	
	@Transactional
	public void remover(Long usuarioId) {
		try {
			repository.deleteById(usuarioId);
			repository.flush();

		} catch (EmptyResultDataAccessException e) {
			throw new UsuarioNaoEncontradoException(usuarioId);
			
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(MSG_USUARIO_EM_USO, usuarioId));
		}
	}
	
	@Transactional
	public void alterarSenha(Long usuarioId, SenhaInput usuarioSenhaInput) {
		Usuario usuario = findByIdOrNotFound(usuarioId);

		String senhaAtual = usuarioSenhaInput.getSenhaAtual();
		String senhaNova = usuarioSenhaInput.getSenhaNova();

		if (usuario.senhaNaoConfere(senhaAtual)) {
			throw new NegocioException("Senha atual não confere com a senha do usuário");
		}
		
		usuario.setSenha(senhaNova);
	}
}
