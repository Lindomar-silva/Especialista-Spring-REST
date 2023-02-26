package com.algaworks.algafood.api.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

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
public class PedidoResumoDTO extends RepresentationModel<PedidoResumoDTO> {

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
	
//	private RestauranteResumoDTO restaurante;
	private RestauranteApenasNomeDTO restaurante;
	private UsuarioDTO cliente;
//	private String nomeCliente;

}
