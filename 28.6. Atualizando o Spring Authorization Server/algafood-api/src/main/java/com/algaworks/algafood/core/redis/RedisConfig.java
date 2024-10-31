package com.algaworks.algafood.core.redis;

import java.util.HashMap;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.session.MapSessionRepository;
import org.springframework.session.Session;
import org.springframework.session.SessionRepository;

@Configuration
public class RedisConfig {
	@Bean
	@Profile("development")
	public SessionRepository<?> sessionRepository() {
		return new MapSessionRepository(new HashMap<String, Session>());
	}
}
