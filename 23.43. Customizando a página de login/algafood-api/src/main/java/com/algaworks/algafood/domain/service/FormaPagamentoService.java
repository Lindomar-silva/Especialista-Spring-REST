package com.algaworks.algafood.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.api.v1.assembler.FormaPagamentoDTOAssembler;
import com.algaworks.algafood.api.v1.model.FormaPagamentoDTO;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.FormaPagamentoNaoEncontradaException;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.repository.FormaPagamentoRepository;

@Service
public class FormaPagamentoService {

	private static final String MSG_FORMA_PAGAMENTO_EM_USO 
	= "Forma de Pagamento de código %d não pode ser removido, pois está em uso!";

	@Autowired
	private FormaPagamentoRepository repository;

	@Autowired
	private FormaPagamentoDTOAssembler assembler;

	public List<FormaPagamento> findAll() {
		return repository.findAll();
	}

	@Transactional
	public FormaPagamentoDTO salvar(FormaPagamento formaPagamento) {
		return assembler.toModel(repository.save(formaPagamento));
	}

	public FormaPagamento findByIdOrNotFound(Long formaPagamentoId) {
		return repository.findById(formaPagamentoId)
				.orElseThrow(() -> new FormaPagamentoNaoEncontradaException(formaPagamentoId));
	}

	@Transactional
	public void remover(Long formaPagamentoId) {
		try {
			repository.deleteById(formaPagamentoId);
			repository.flush();

		} catch (EmptyResultDataAccessException e) {
			throw new FormaPagamentoNaoEncontradaException(formaPagamentoId);

		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(MSG_FORMA_PAGAMENTO_EM_USO, formaPagamentoId));
		}
	}

}
