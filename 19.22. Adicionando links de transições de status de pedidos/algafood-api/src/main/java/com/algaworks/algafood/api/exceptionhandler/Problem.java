package com.algaworks.algafood.api.exceptionhandler;

import java.time.OffsetDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel("Problema")
@JsonInclude(Include.NON_NULL)
@Getter
@Setter
public class Problem {

	@ApiModelProperty(example = "2022-11-09T13:34:32.526758Z" , position = 1)
	private OffsetDateTime timestamp;
	
	@ApiModelProperty(example = "400", position = 5)
	private Integer status;
	
	@ApiModelProperty(example = "https://algafood.com.br/dados-invalidos", position = 10)
	private String type;
	
	@ApiModelProperty(example = "Dados inválidos", position = 15)
	private String title;
	
	@ApiModelProperty(example = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente!", position = 20)
	private String detail;
	
	@ApiModelProperty(example = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente!", position = 25)
	private String userMsg;
	
	@ApiModelProperty(value = "Lista de objetos campos que geraram o erro (opcional)", position = 30)
	private List<Object> objects;

	@ApiModel("ObjetoProblema")
	@Getter
	@Setter
	public static class Object {
		
		@ApiModelProperty(example = "preco")
		private String name;
		
		@ApiModelProperty(example = "O preço é obrigatório!")
		private String userMsg;
		
		public Object() {}
		public Object(String name, String userMsg) {
			this.name = name;
			this.userMsg = userMsg;
		}
		
	}

	public Problem() {}
	public Problem(OffsetDateTime timestamp, Integer status, String type, String title, String detail, String userMsg,
			List<Object> objects) {
		this.timestamp = timestamp;
		this.status = status;
		this.type = type;
		this.title = title;
		this.detail = detail;
		this.userMsg = userMsg;
		this.objects = objects;
	}
}
