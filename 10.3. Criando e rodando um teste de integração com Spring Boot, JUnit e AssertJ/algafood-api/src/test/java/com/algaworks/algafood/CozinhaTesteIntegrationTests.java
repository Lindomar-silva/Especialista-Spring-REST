package com.algaworks.algafood;

import static org.assertj.core.api.Assertions.assertThat;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CozinhaTesteIntegrationTests {

	@Autowired
	private CadastroCozinhaService service;

	@Test
	public void testarCadastroCozinhaComSusesso() {
		// Cenário
		Cozinha novaCozinha = new Cozinha();
		novaCozinha.setNome("Chineza");

		// Ação
		novaCozinha = service.salvar(novaCozinha);

		// Validação
		assertThat(novaCozinha).isNotNull();
		assertThat(novaCozinha.getId()).isNotNull();
	}

	@Test
	public void testarCadastroCozinha_QuandoSemNome() {
		Cozinha cozinha = new Cozinha();
		cozinha.setNome(null);

		ConstraintViolationException erro = 
				Assertions.assertThrows(ConstraintViolationException.class, () -> {
					service.salvar(cozinha);
		});
		assertThat(erro).isNotNull();
	}
}