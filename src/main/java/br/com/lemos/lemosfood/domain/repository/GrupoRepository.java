package br.com.lemos.lemosfood.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.lemos.lemosfood.domain.model.Grupo;

@Repository
public interface GrupoRepository extends JpaRepository<Grupo, Long> {

}
