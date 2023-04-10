package com.algaworks.algafood.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.api.v1.assembler.UsuarioAssembler;
import com.algaworks.algafood.api.v1.model.UsuarioDTO;
import com.algaworks.algafood.api.v1.model.input.SenhaInput;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.UsuarioNaoEncontradoException;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.UsuarioRepository;

@Service
public class UsuarioService {

	private static final String MSG_USUARIO_EM_USO 
	= "Usuário de código %d não pode ser removido, pois está em uso!";
	
	@Autowired
	private UsuarioRepository repository;
	
	@Autowired
	private GrupoService grupoService;
	
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
		existeEmailCadastrado(usuario);
		return assembler.toModel(repository.save(usuario));
	}
	
	private void existeEmailCadastrado(Usuario usuario) {
		repository.detach(usuario);
		Optional<Usuario> usuarioCadastrado = repository.findTop1ByEmail(usuario.getEmail());

		if (usuarioCadastrado.isPresent() && !usuarioCadastrado.get().equals(usuario)) {
//		if (repository.existsByEmailAndIdNot(usuario.getEmail(), usuario.getId())) {
			throw new NegocioException(
					String.format("Já existe um usuário cadastrado com o e-mail %s", usuario.getEmail()));
		}
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

	@Transactional
	public void associarGrupo(Long usuarioId, Long grupoId) {
		Usuario usuario = findByIdOrNotFound(usuarioId);

		if (usuario.notExistGrupo(grupoId)) {
			Grupo grupo = grupoService.findByIdOrNotFound(grupoId);
			usuario.adicionarGrupo(grupo);
		}
	}

	@Transactional
	public void desassociarGrupo(Long usuarioId, Long grupoId) {
		Usuario usuario = findByIdOrNotFound(usuarioId);

		if (usuario.notExistGrupo(grupoId)) {
			throw new NegocioException(String.format("O grupo de código '%d' não esta associado ao Usuário '%s'",
					grupoId, usuario.getNome()));
		}

		Grupo grupo = grupoService.findByIdOrNotFound(grupoId);
		usuario.removerGrupo(grupo);
	}
}
