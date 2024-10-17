package com.algaworks.algafood.api.exceptionhandler;

import java.time.OffsetDateTime;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@JsonInclude(Include.NON_NULL)
@Getter
@Setter
@Schema(name = "Problema")
public class Problem {

	@Schema(example = "400")
	private Integer status;

	@Schema(example = "https://algafood.com.br/dados-invalidos")
	private String type;

	@Schema(example = "Dados inválidos")
	private String title;

	@Schema(example = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente!")
	private String detail;

	@Schema(example = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente!")
	private String userMsg;

	@Schema(example = "2024-06-25T15:37:26.902245498Z")
	private OffsetDateTime timestamp;

	@Schema(description = "Lista de objetos ou campos que geraram o erro")
	private List<Object> objects;

	@Getter
	@Setter
	@Schema(name = "ObjetoProblema")
	public static class Object {

		@Schema(example = "preco")
		private String name;

		@Schema(example = "O preço é inválido")
		private String userMsg;

		public Object() {
		}

		public Object(String name, String userMsg) {
			this.name = name;
			this.userMsg = userMsg;
		}

	}

	public Problem() {
	}

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
