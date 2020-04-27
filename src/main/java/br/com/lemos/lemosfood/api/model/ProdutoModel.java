package br.com.lemos.lemosfood.api.model;

import lombok.Setter;

import java.math.BigDecimal;

import lombok.Getter;

@Setter
@Getter
public class ProdutoModel {

	private Long id;
	private String nome;
	private String descricao;
	private BigDecimal preco;
	private Boolean ativo;
}