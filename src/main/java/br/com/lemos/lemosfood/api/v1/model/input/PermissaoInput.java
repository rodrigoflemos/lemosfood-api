package br.com.lemos.lemosfood.api.v1.model.input;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PermissaoInput {

	@NotBlank
	private String nome;
	
	private String descricao;
}
