package br.com.lemos.lemosfood.domain.event;

import br.com.lemos.lemosfood.domain.model.Pedido;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PedidoCanceladoEvent {

	private Pedido pedido;
}
