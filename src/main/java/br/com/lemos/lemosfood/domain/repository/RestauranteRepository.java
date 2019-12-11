package br.com.lemos.lemosfood.domain.repository;

import java.util.List;

import br.com.lemos.lemosfood.domain.model.Restaurante;

public interface RestauranteRepository {

	List<Restaurante> listar();
	Restaurante buscar (Long id);
	Restaurante salvar (Restaurante restaurante);
	void remover (Long id);
}
