package br.com.lemos.lemosfood.domain.repository;

import java.time.OffsetDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.lemos.lemosfood.domain.model.FormaPagamento;

public interface FormaPagamentoRepository extends JpaRepository<FormaPagamento, Long>{

	@Query("select max(dataAtualizacao) from FormaPagamento")
	OffsetDateTime getUDataUltimaAtualizacao();
	
	@Query("select dataAtualizacao from FormaPagamento where id = :formaPagamentoId")
	OffsetDateTime getDataAtualizacaoById(Long formaPagamentoId); 
}