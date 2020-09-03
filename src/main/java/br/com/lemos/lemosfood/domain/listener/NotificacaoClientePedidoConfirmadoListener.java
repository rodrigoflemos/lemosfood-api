package br.com.lemos.lemosfood.domain.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import br.com.lemos.lemosfood.domain.event.PedidoConfirmadoEvent;
import br.com.lemos.lemosfood.domain.model.Pedido;
import br.com.lemos.lemosfood.domain.service.EnvioEmailService;
import br.com.lemos.lemosfood.domain.service.EnvioEmailService.Mensagem;

@Component
public class NotificacaoClientePedidoConfirmadoListener {

	@Autowired
	private EnvioEmailService envioEmail;
	
	@TransactionalEventListener
	public void aoConfirmarPedido(PedidoConfirmadoEvent event) {
		
		Pedido pedido = event.getPedido();
		
		var mensagem = Mensagem.builder()
				.assunto(pedido.getRestaurante().getNome() + " - Pedido confirmado")
				.corpo("emails/pedido-confirmado.html")
				.variavel("pedido", pedido)
				.destinatario(pedido.getCliente().getEmail())
				.build();
		
		envioEmail.enviar(mensagem);
	}
}
