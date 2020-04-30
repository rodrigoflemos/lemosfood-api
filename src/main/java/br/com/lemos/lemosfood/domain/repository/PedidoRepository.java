package br.com.lemos.lemosfood.domain.repository;

import org.springframework.stereotype.Repository;

import br.com.lemos.lemosfood.domain.model.Pedido;

@Repository
public interface PedidoRepository extends CustomJpaRepository<Pedido, Long>{

}
