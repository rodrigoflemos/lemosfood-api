package br.com.lemos.lemosfood.domain.repository;

import java.util.List;

import br.com.lemos.lemosfood.domain.model.Cozinha;

public interface CozinhaRepository {

	List<Cozinha> listar();
	Cozinha buscar (Long id);
	Cozinha salvar (Cozinha cozinha);
	void remover (Cozinha cozinha);
}
