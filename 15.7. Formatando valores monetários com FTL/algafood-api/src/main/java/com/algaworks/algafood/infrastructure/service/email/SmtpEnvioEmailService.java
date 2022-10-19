package com.algaworks.algafood.infrastructure.service.email;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.algaworks.algafood.core.email.EmailProperties;
import com.algaworks.algafood.domain.service.EnvioEmailService;

import freemarker.template.Configuration;
import freemarker.template.Template;

@Service
public class SmtpEnvioEmailService implements EnvioEmailService {

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private EmailProperties properties;

	@Autowired
	private Configuration freeMarker;

	@Override
	public void enviar(Mensagem mensagem) {
		try {
			String corpo = processarTemplates(mensagem);

			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");

			helper.setFrom(properties.getRemetente());
			helper.setTo(mensagem.getDestinatarios().toArray(new String[0]));
			helper.setSubject(mensagem.getAssunto());
			helper.setText(corpo, true);

			mailSender.send(message);

		} catch (Exception e) {
			throw new EmailException("Não é possível enviar e-mail", e);
		}
	}

	private String processarTemplates(Mensagem mensagem) {
		try {
			Template template = freeMarker.getTemplate(mensagem.getCorpo());

			return FreeMarkerTemplateUtils.processTemplateIntoString(template, mensagem.getVariaveis());

		} catch (Exception e) {
			throw new EmailException("Não foi possível montar o templates do e-mail", e);
		}
	}
}
