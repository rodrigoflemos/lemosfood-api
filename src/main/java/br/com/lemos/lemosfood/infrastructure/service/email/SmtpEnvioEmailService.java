package br.com.lemos.lemosfood.infrastructure.service.email;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import br.com.lemos.lemosfood.core.email.EmailProperties;
import br.com.lemos.lemosfood.domain.service.EnvioEmailService;
import freemarker.template.Configuration;
import freemarker.template.Template;

public class SmtpEnvioEmailService implements EnvioEmailService{

	@Autowired
	JavaMailSender mailSender;
	
	@Autowired
	EmailProperties emailProperties;
	
	@Autowired
	Configuration freeMarkerConfig;
	
	@Override
	public void enviar(Mensagem mensagem) {
		
		try {
			String corpo = processarTemplate(mensagem);
			
			MimeMessage mimeMessage = mailSender.createMimeMessage();
		
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
		
			helper.setFrom(emailProperties.getRemetente());
			helper.setTo(mensagem.getDestinatarios().toArray(new String[0]));
			helper.setSubject(mensagem.getAssunto());
			helper.setText(corpo, true);
			
		
			mailSender.send(mimeMessage);
		}catch (Exception e) {
			throw new EmailException("Não foi possivel enviar e-mail", e);
		}
	}
	
	protected String processarTemplate(Mensagem mensagem) {
		
		try {
			Template template = freeMarkerConfig.getTemplate(mensagem.getCorpo());
			return FreeMarkerTemplateUtils.processTemplateIntoString(
					template, mensagem.getVariaveis());
		}catch (Exception e) {
			throw new EmailException("Não foi possível montar o template do e-mail", e); 
		}			
	}
}