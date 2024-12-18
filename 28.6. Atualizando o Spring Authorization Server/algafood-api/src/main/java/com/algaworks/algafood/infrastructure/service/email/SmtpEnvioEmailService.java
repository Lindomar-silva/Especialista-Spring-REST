package com.algaworks.algafood.infrastructure.service.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.algaworks.algafood.core.email.EmailProperties;
import com.algaworks.algafood.domain.service.EnvioEmailService;

public class SmtpEnvioEmailService implements EnvioEmailService {

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private EmailProperties properties;

	@Autowired
	private ProcessadorEmailTemplate processarTemplate;

	@Override
	public void enviar(Mensagem mensagem) {
		try {
			MimeMessage mimeMessage = criarMimeMessage(mensagem);
			mailSender.send(mimeMessage);

		} catch (Exception e) {
			throw new EmailException("Não é possível enviar e-mail", e);
		}
	}

	protected MimeMessage criarMimeMessage(Mensagem mensagem) throws MessagingException {
		String corpo = processarTemplate.processarTemplate(mensagem);

		MimeMessage mimeMessage = mailSender.createMimeMessage();

		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
		helper.setFrom(properties.getRemetente());
		helper.setTo(mensagem.getDestinatarios().toArray(new String[0]));
		helper.setSubject(mensagem.getAssunto());
		helper.setText(corpo, true);

		return mimeMessage;
	}

}
