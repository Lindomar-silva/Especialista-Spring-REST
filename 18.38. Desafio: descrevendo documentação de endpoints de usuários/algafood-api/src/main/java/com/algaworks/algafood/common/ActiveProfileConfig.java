package com.algaworks.algafood.common;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ActiveProfileConfig implements CommandLineRunner {

	@Value(value = "${spring.profiles.active:}")
	private String activeProfile;

	@Override
	public void run(String... args) throws Exception {
		if (StringUtils.isNotBlank(activeProfile)) {

			String msgConsole = ("Running in " + activeProfile + " environment").toUpperCase();

			String ansiColor = activeProfile.contains("dev") ? ConsoleColors.GREEN
					: activeProfile.contains("test") ? ConsoleColors.CYAN : ConsoleColors.PURPLE;

			System.out.println(ansiColor + msgConsole + ConsoleColors.RESET);
		}
	}
}

//Spring Profiles
//https://www.baeldung.com/spring-profiles