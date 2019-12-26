package br.com.lemos.lemosfood.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.lemos.lemosfood.domain.model.Estado;

public interface EstadoRepository extends JpaRepository<Estado, Long>{
	
}
