package com.algaworks.algafood.api.exceptionhandler;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

@JsonInclude(Include.NON_NULL)
@Getter
@Setter
public class Problem {

	private Integer status;
	private String type;
	private String title;
	private String detail;

	private String userMsg;
	private LocalDateTime timestamp;

	public Problem() {
	}

	public Problem(Integer status, String type, String title, String detail, String userMsg, LocalDateTime timestamp) {
		this.status = status;
		this.type = type;
		this.title = title;
		this.detail = detail;
		this.userMsg = userMsg;
		this.timestamp = timestamp;
	}

}
