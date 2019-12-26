package br.com.lemos.lemosfood.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.lemos.lemosfood.domain.model.FormaPagamento;

public interface FormaPagamentoRepository extends JpaRepository<FormaPagamento, Long>{

}
