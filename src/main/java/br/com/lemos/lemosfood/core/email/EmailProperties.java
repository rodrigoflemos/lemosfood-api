package br.com.lemos.lemosfood.core.email;

import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import lombok.Getter;
import lombok.Setter;

@Validated
@Getter
@Setter
@Component
@ConfigurationProperties("lemosfood.email")
public class EmailProperties {

	private Implementacao impl = Implementacao.FAKE;
	
	@NotNull
	private String remetente;
	
	public enum Implementacao {
	    SMTP, FAKE
	}
}
