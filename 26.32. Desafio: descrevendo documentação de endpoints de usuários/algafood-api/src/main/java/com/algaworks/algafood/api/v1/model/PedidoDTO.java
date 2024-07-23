package com.algaworks.algafood.api.v1.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

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
public class PedidoDTO extends RepresentationModel<PedidoDTO> {

	@Schema(example = "f9981ca4-5a5e-4da3-af04-933861df3e55")
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

	@Schema(example = "2024-06-29T15:33:08Z")
	private OffsetDateTime dataConfirmacao;

	@Schema(example = "2024-06-29T15:53:08Z")
	private OffsetDateTime dataEntrega;

	@Schema(example = "2024-06-29T15:32:08Z")
	private OffsetDateTime dataCancelamento;

//	private RestauranteResumoDTO restaurante;
	private RestauranteApenasNomeDTO restaurante;
	private UsuarioDTO cliente;
	private FormaPagamentoDTO formaPagamento;
	private EnderecoDTO enderecoEntrega;
	private List<ItemPedidoDTO> itens;
}
