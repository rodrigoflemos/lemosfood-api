package br.com.lemos.lemosfood.api.model.input;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

import br.com.lemos.lemosfood.core.validation.FileSize;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FotoProdutoInput {

	@NotNull
	@FileSize(max = "100KB")
	private MultipartFile arquivo;
	
	@NotBlank
	private String descricao;
}