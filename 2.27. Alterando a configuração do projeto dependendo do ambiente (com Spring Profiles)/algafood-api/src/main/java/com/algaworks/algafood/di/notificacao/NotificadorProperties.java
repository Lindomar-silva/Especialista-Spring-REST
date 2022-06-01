package com.algaworks.algafood.di.notificacao;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "notificador.email")
public class NotificadorProperties {

	private String hostServer;
	private Integer portaServer;

	public String getHostServer() {
		return hostServer;
	}

	public void setHostServer(String hostServer) {
		this.hostServer = hostServer;
	}

	public Integer getPortaServer() {
		return portaServer;
	}

	public void setPortaServer(Integer portaServer) {
		this.portaServer = portaServer;
	}

}
