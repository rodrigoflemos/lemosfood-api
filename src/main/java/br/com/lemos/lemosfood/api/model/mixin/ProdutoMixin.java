package br.com.lemos.lemosfood.api.model.mixin;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.lemos.lemosfood.domain.model.Restaurante;

public abstract class ProdutoMixin {
	
	@JsonIgnore
	private Restaurante restaurante;

}
