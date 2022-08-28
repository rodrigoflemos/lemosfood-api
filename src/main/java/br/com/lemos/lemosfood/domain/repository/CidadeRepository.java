package br.com.lemos.lemosfood.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.lemos.lemosfood.domain.model.Cidade;

public interface CidadeRepository extends JpaRepository<Cidade, Long> {

}
