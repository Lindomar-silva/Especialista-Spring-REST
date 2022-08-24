package com.algaworks.algafood.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.api.assembler.CidadeDTOAssembler;
import com.algaworks.algafood.api.model.CidadeDTO;
import com.algaworks.algafood.domain.exception.CidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.CidadeRepository;

@Service
public class CidadeService {

	private static final String MSG_CIDADE_EM_USO 
	= "Cidade de código %d não pode ser removida, pois está em uso";

	@Autowired
	private CidadeDTOAssembler assembler;
	
	@Autowired
	private CidadeRepository cidadeRepo;

	@Autowired
	private EstadoService estadoService;

	public List<Cidade> listar() {
		return cidadeRepo.findAll();
	}

	@Transactional
	public CidadeDTO salvar(Cidade cidade) {
		try {
			Long estadoId = cidade.getEstado().getId();
			Estado estado = estadoService.findByIdOrNotFound(estadoId);
			cidade.setEstado(estado);
			
			return assembler.toDTO(cidadeRepo.save(cidade));
			
		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	@Transactional
	public void remover(Long cidadeId) {
		try {
			cidadeRepo.deleteById(cidadeId);
			cidadeRepo.flush(); //Evita erro de DataIntegrityViolationException fora do bloco try catch

		} catch (EmptyResultDataAccessException e) {
			throw new CidadeNaoEncontradaException(cidadeId);
			
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format(MSG_CIDADE_EM_USO, cidadeId));
		}
	}
	
	public Cidade findByIdOrNotFound(Long cidadeId) {
		return cidadeRepo.findById(cidadeId)
				.orElseThrow(() -> new CidadeNaoEncontradaException(cidadeId));
	}
}
