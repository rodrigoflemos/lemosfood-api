package br.com.lemos.lemosfood.domain.repository;

import java.math.BigDecimal;
import java.util.List;

import br.com.lemos.lemosfood.domain.model.Restaurante;

public interface RestauranteRepositoryQueries {
	
	List<Restaurante> find(String nome, 
			BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal);
	
	List<Restaurante> findComFreteGratis(String nome);

}
