package com.algaworks.algafood;

import static org.hamcrest.CoreMatchers.equalTo;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;

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
public class CadastroRestauranteIT {

	private static final String VIOLACAO_NEGOCIO = "Violação de regra de negócio";
	private static final String DADOS_INVALIDOS = "Dados inválidos";
	private static final int ID_INEXISTENTE = 100;

	@LocalServerPort
	private int port;

	@Autowired
	DatabaseCleaner databaseCleaner;
	
	@Autowired
	private CozinhaRepository cozRepository;
	
	@Autowired
	private RestauranteRepository restRepository;

	private Restaurante restauranteAtualizado;
	private String restaurantePanelaDeFerroJson;
	private String restauranteAtualizadoJson;
	private String restauranteCozinhaInexistente;
	private String restauranteSemCozinha;
	private String restauranteSemFrete;
	
	@BeforeEach
	public void setUp() {
		// Habiliatando o log da resposta quando falhar
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

		RestAssured.port = port;
		RestAssured.basePath = "/restaurantes";

		restaurantePanelaDeFerroJson = ResourceUtils.getContentFromResource(
				"/json/restaurante-panela-de-ferro.json");
		
		restauranteAtualizadoJson = ResourceUtils.getContentFromResource(
				"/json/restaurante-atualizado.json");
		
		restauranteSemFrete = ResourceUtils.getContentFromResource(
				"/json/restaurante-panela-de-ferro-sem-frete.json");
		
		restauranteSemCozinha = ResourceUtils.getContentFromResource(
				"/json/restaurante-panela-de-ferro-sem-cozinha.json");
		
		restauranteCozinhaInexistente = ResourceUtils.getContentFromResource(
				"/json/restaurante-panela-de-ferro-com-cozinha-inexistente.json");
		
		databaseCleaner.clearTables();	
		
		prepararDados();
	}
	
	@Test
	public void deveRetornarStatus200_QuandoConsultarRestaurante() {
		RestAssured.given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.statusCode(HttpStatus.OK.value());
	}
	
	@Test
	public void deveRetornarStatus201_QuandoCadastrarRestaurante() {
		RestAssured.given()
			.body(restaurantePanelaDeFerroJson)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.CREATED.value());
	}
	
	@Test
	public void deveRetornarStatus400_QuandoCadastrarRestauranteComCozinhaInexistente() {
		RestAssured.given()
			.body(restauranteCozinhaInexistente)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.BAD_REQUEST.value())
			.body("title", equalTo(VIOLACAO_NEGOCIO));
	}
	
	@Test
	public void deveRetornarStatus200_QuandoPequisarRestauranteExistente() {
		RestAssured.given()
			.pathParam("restauranteId", restauranteAtualizado.getId())
			.accept(ContentType.JSON)
		.when()
			.get("/{restauranteId}")
		.then()
			.statusCode(HttpStatus.OK.value())
			.body("nome", equalTo(restauranteAtualizado.getNome()));
	}
	
	@Test
	public void deveRetornarStatus404_QuandoPequisarRestauranteInexistente() {
		RestAssured.given()
			.pathParam("restauranteId", ID_INEXISTENTE)
			.accept(ContentType.JSON)
		.when()
			.get("/{restauranteId}")
		.then()
			.statusCode(HttpStatus.NOT_FOUND.value());
	}
	
	@Test
	public void deveRetornarStatus400_QuandoCadastrarRestauranteSemFrete() {
		RestAssured.given()
			.body(restauranteSemFrete)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.BAD_REQUEST.value())
			.body("title", equalTo(DADOS_INVALIDOS));
	}
	
	@Test
	public void deveRetornarStatus400_QuandoCadastrarRestauranteSemCozinha() {
		RestAssured.given()
			.body(restauranteSemCozinha)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.BAD_REQUEST.value())
			.body("title", equalTo(DADOS_INVALIDOS));
	}
	
	@Test
	public void deveRetornarStatus200_QuandoPesquisarRestaurante() {
		RestAssured.given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.statusCode(HttpStatus.OK.value());
	}
	
	@Test
	public void deveRetornarStatus200_QuandoAtualizarRestaurante() {
		RestAssured.given()
			.pathParam("restauranteId", restauranteAtualizado.getId())
			.body(restauranteAtualizadoJson)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.put("/{restauranteId}")
		.then()
			.statusCode(HttpStatus.OK.value());
	}
	
	private void prepararDados() {
		Cozinha cozinha1 = new Cozinha();
		cozinha1.setNome("Tailandesa");
		cozRepository.save(cozinha1);

		Restaurante restaurante1 = new Restaurante();
		restaurante1.setNome("Restaurante Sabor Mineiro");
		restaurante1.setTaxaFrete(new BigDecimal(3.5));
		restaurante1.setCozinha(cozinha1);
		restRepository.save(restaurante1);
		
		Cozinha cozinha2 = new Cozinha();
		cozinha2.setNome("Americana");
		cozRepository.save(cozinha2);
		
		Restaurante restaurante2 = new Restaurante();
		restaurante2.setNome("Restaurante Dona Maria");
		restaurante2.setTaxaFrete(new BigDecimal(4.5));
		restaurante2.setCozinha(cozinha2);
		restRepository.save(restaurante2);
		
		restauranteAtualizado = new Restaurante();
		restauranteAtualizado.setNome("Reatuarante Paulista");
		restauranteAtualizado.setTaxaFrete(new BigDecimal(4.3));
		restauranteAtualizado.setCozinha(cozinha1);
		restRepository.save(restauranteAtualizado);
	}
}
