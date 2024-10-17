package com.algaworks.algafood;

import static org.hamcrest.CoreMatchers.equalTo;

import java.math.BigDecimal;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.util.DatabaseCleaner;
import com.algaworks.algafood.util.ResourceUtils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@SuppressWarnings("deprecation")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class CadastroCozinhaIT {
	
	@LocalServerPort
	private int port;
	
	@Autowired
	private DatabaseCleaner databaseCleaner;
	
	@Autowired
	private CozinhaRepository cozRepository;

	@Autowired
	private RestauranteRepository restRepository;
	
	private static final int ID_INEXISTENTE = 200;
	
	private int qtdeCozinha;
	Cozinha cozinhaExistente;
	Cozinha cozinhaSemRestaurante;
	private String cozinhaArgentinaJson;
	
	
	@BeforeEach
	public void setUp() {
		// Habiliatando o log da resposta quando falhar
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
	
		RestAssured.port = port;
		RestAssured.basePath = "/cozinhas";
		
		cozinhaArgentinaJson = ResourceUtils.getContentFromResource(
				"/json/cozinha-argentina.json");
		
		databaseCleaner.clearTables();
		prepararDados();
	}
	
	@Test
	public void deveRetornarStatus200_QuandoConsultarCozinhas() {
		RestAssured.given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.statusCode(HttpStatus.OK.value());
	}
	
	@Test
	public void deveConterUmaOuMaisCozinhas_QuandoConsultarCozinhas() {
		RestAssured.given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.body("",Matchers.hasSize(qtdeCozinha));
	}
	
	@Test
	public void deveRetornarStatus201_QuandoCadastrarCozinha() {
		RestAssured.given()
			.body(cozinhaArgentinaJson)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.CREATED.value());
	}
	
	@Test
	public void deveRetornarRespostaEStatusCorretos_QuandoConsultarCozinhaExistente() {
		RestAssured.given()
			.pathParam("cozinhaId", cozinhaExistente.getId())
			.accept(ContentType.JSON)
		.when()
			.get("/{cozinhaId}")
		.then()
			.statusCode(HttpStatus.OK.value())
			.body("nome", equalTo(cozinhaExistente.getNome()));
	}
	
	@Test
	public void deveRetornarStatus404_QuandoConsultarCozinhaInexistente() {
		RestAssured.given()
			.pathParam("cozinhaId", ID_INEXISTENTE)
			.accept(ContentType.JSON)
		.when()
			.get("/{cozinhaId}")
		.then()
			.statusCode(HttpStatus.NOT_FOUND.value());
	}
	
	@Test
	public void deveRetornarStatus409_QuandoRemoverCozinhaEmUso() {
		RestAssured.given()
			.pathParam("cozinhaId", cozinhaExistente.getId())
			.accept(ContentType.JSON)
		.when()
			.delete("/{cozinhaId}")
		.then()
			.statusCode(HttpStatus.CONFLICT.value());
	}
	
	@Test
	public void deveRetornarStatus204_QuandoRemoverCozinhaSemRestaurante() {
		RestAssured.given()
			.pathParam("cozinhaId", cozinhaSemRestaurante.getId())
			.accept(ContentType.JSON)
		.when()
			.delete("/{cozinhaId}")
		.then()
			.statusCode(HttpStatus.NO_CONTENT.value());
	}
	
	@Transactional
	private void prepararDados() {
		cozinhaSemRestaurante = new Cozinha();
		cozinhaSemRestaurante.setNome("Tailandesa");
		cozRepository.save(cozinhaSemRestaurante);

		Cozinha cozinha2 = new Cozinha();
		cozinha2.setNome("Americana");
		cozRepository.save(cozinha2);
		
		cozinhaExistente = new Cozinha();
		cozinhaExistente.setNome("Africana");
		cozRepository.save(cozinhaExistente);
		
		Cozinha cozinha3 = new Cozinha();
		cozinha3.setNome("Brasileira");
		cozRepository.save(cozinha3);
		
		Restaurante restaurante1 = new Restaurante();
		restaurante1.setNome("Restaurante Sabor Mineiro");
		restaurante1.setTaxaFrete(new BigDecimal(3.5));
		restaurante1.setCozinha(cozinhaExistente);
		restRepository.save(restaurante1);
		
		qtdeCozinha = (int) cozRepository.count();
	}
}