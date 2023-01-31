package com.algaworks.algafood.api.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "pedidos")
@JsonInclude(Include.NON_NULL)
@Getter
@Setter
public class PedidoDTO extends RepresentationModel<PedidoDTO> {
	
	@ApiModelProperty(example = "c08f9902-47fe-4798-a2ee-41f2cbf44edd")
	private String uuidCod;
	
	@ApiModelProperty(example = "236.45")
	private BigDecimal subtotal;
	
	@ApiModelProperty(example = "8.5")
	private BigDecimal taxaFrete;
	
	@ApiModelProperty(example = "244.95")
	private BigDecimal valorTotal;
	
	@ApiModelProperty(example = "CRIADO")
	private String status; 
	
	@ApiModelProperty(example = "2022-12-07T15:52:22Z")
	private OffsetDateTime dataCriacao;
	
	@ApiModelProperty(example = "2022-12-07T15:53:12Z")
	private OffsetDateTime dataConfirmacao;
	
	@ApiModelProperty(example = "2022-12-07T15:58:32Z")
	private OffsetDateTime dataEntrega;
	
	@ApiModelProperty(example = "2022-12-07T15:52:30Z")
	private OffsetDateTime dataCancelamento;
	
	private RestauranteResumoDTO restaurante;
	private UsuarioDTO cliente;
	private FormaPagamentoDTO formaPagamento;
	private EnderecoDTO enderecoEntrega;
	private List<ItemPedidoDTO> itens;
}
