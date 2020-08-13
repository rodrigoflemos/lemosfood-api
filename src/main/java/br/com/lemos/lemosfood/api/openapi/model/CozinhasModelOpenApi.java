package br.com.lemos.lemosfood.api.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import br.com.lemos.lemosfood.api.model.CidadeModel;
import br.com.lemos.lemosfood.api.model.CozinhaModel;
import br.com.lemos.lemosfood.api.openapi.model.CidadesModelOpenApi.CidadeEmbeddedModelOpenApi;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@ApiModel("CozinhasModel")
@Setter
@Getter
public class CozinhasModelOpenApi {
	
	private CozinhasEmbeddedModelOpenApi _embedded;
	private Links _links;
	private PageModelOpenApi page;
	
	@ApiModel("CozinhasEmbeddedModel")
	@Data
	public class CozinhasEmbeddedModelOpenApi {
	
		private List<CozinhaModel> cozinhas;
	}
	
}