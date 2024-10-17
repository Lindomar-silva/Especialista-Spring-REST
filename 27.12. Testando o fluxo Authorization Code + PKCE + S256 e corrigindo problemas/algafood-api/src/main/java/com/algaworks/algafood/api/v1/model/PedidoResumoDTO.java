package com.algaworks.algafood.api.v1.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "pedidos")
@JsonInclude(Include.NON_NULL)
@Getter
@Setter
public class PedidoResumoDTO extends RepresentationModel<PedidoResumoDTO> {

	@Schema(example = "04813f77-79b5-11ec-9a17-0242ac1b0002")
	private String uuidCod;

	@Schema(example = "298.90")
	private BigDecimal subtotal;

	@Schema(example = "10.00")
	private BigDecimal taxaFrete;

	@Schema(example = "308.90")
	private BigDecimal valorTotal;

	@Schema(example = "CRIADO")
	private String status;

	@Schema(example = "2024-06-29T15:31:08Z")
	private OffsetDateTime dataCriacao;

//	private RestauranteResumoDTO restaurante;
	private RestauranteApenasNomeDTO restaurante;
	private UsuarioDTO cliente;
//	private String nomeCliente;

}
