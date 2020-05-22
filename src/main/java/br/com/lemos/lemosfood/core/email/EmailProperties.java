package br.com.lemos.lemosfood.core.email;

import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import lombok.Getter;
import lombok.Setter;

@Validated
@Getter
@Setter
@ConfigurationProperties("lemosfood.email")
public class EmailProperties {

	@NotNull
	private String remetente;
}
