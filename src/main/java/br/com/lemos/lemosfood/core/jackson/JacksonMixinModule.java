package br.com.lemos.lemosfood.core.jackson;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.module.SimpleModule;

import br.com.lemos.lemosfood.api.model.mixin.CidadeMixin;
import br.com.lemos.lemosfood.api.model.mixin.CozinhaMixin;
import br.com.lemos.lemosfood.api.model.mixin.ProdutoMixin;
import br.com.lemos.lemosfood.domain.model.Cidade;
import br.com.lemos.lemosfood.domain.model.Cozinha;
import br.com.lemos.lemosfood.domain.model.Produto;

@Component
public class JacksonMixinModule extends SimpleModule{

	private static final long serialVersionUID = 1L;

	public JacksonMixinModule() {
		setMixInAnnotation(Cozinha.class, CozinhaMixin.class);
		setMixInAnnotation(Cidade.class, CidadeMixin.class);
		setMixInAnnotation(Produto.class, ProdutoMixin.class);
	}
}