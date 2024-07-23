package com.algaworks.algafood.api.exceptionhandler;

import java.time.OffsetDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;
@JsonInclude(Include.NON_NULL)
@Getter
@Setter
public class Problem {

	private OffsetDateTime timestamp;
	
	private Integer status;
	
	private String type;
	
	private String title;
	
	private String detail;
	
	private String userMsg;
	
	private List<Object> objects;

	@Getter
	@Setter
	public static class Object {
		
		private String name;
		
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
