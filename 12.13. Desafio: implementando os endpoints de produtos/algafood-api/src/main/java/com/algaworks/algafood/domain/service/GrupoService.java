package com.algaworks.algafood.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.api.assembler.GrupoAssembler;
import com.algaworks.algafood.api.model.GrupoDTO;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.GrupoNaoEncontradoException;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.repository.GrupoReposytory;

@Service
public class GrupoService {

	private static final String MSG_GRUPO_EM_USO 
	= "Grupo de código %d não pode ser removido, pois está em uso!";
	
	@Autowired
	private GrupoReposytory reposytory;
	
	@Autowired
	private GrupoAssembler assembler;

	public List<Grupo> findAll() {
		return reposytory.findAll();
	}

	public Grupo findByIdOrNotFound(Long grupoId) {
		return reposytory.findById(grupoId)
				.orElseThrow(() -> new GrupoNaoEncontradoException(grupoId));
	}
	
	@Transactional
	public GrupoDTO salvar(Grupo grupo) {
		return assembler.toDTO(reposytory.save(grupo));
	}
	
//	@Transactional
	public void remover(Long grupoId) {
		try {
			reposytory.deleteById(grupoId);
			reposytory.flush();

		} catch (EmptyResultDataAccessException e) {
			throw new GrupoNaoEncontradoException(grupoId);

		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(MSG_GRUPO_EM_USO, grupoId));
		}
	}
}
