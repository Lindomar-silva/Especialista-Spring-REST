package com.algaworks.algafood.api.exceptionhandler;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

@JsonInclude(Include.NON_NULL)
@Getter
@Setter
public class Problem {

	private LocalDateTime timestamp;
	private Integer status;
	private String type;
	private String title;
	private String detail;
	private String userMsg;
	private List<Field> fields;

	@Getter
	@Setter
	public static class Field {
		private String name;
		private String userMsg;

		public Field() {
		}

		public Field(String name, String userMsg) {
			this.name = name;
			this.userMsg = userMsg;
		}

	}

	public Problem() {
	}

	public Problem(LocalDateTime timestamp, Integer status, String type, String title, String detail, String userMsg,
			List<Field> fields) {
		this.timestamp = timestamp;
		this.status = status;
		this.type = type;
		this.title = title;
		this.detail = detail;
		this.userMsg = userMsg;
		this.fields = fields;
	}

}
