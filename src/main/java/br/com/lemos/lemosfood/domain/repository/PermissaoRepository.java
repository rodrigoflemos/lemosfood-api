package br.com.lemos.lemosfood.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.lemos.lemosfood.domain.model.Permissao;

public interface PermissaoRepository extends JpaRepository<Permissao, Long>{

}
