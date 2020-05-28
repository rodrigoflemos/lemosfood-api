package br.com.lemos.lemosfood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.lemos.lemosfood.domain.model.Pedido;
import br.com.lemos.lemosfood.domain.repository.PedidoRepository;

@Service
public class FluxoPedidoService {

	@Autowired
	private EmissaoPedidoService emissaoPedido;
	
	/*
	 * Para o disparo do evento, é necessário chamar o save do repository,
	 * mesmo com o objeto sendo gerenciado pelo EntityManager.
	 * 
	 * Como o RegisterEvent é parte do Spring Data, o funcionamento está atrelado ao save()
	 *
	 * 
	 */
	@Autowired
	private PedidoRepository pedidoRepository;
		
	@Transactional
	public void confirmar(String codigoPedido) {
		Pedido pedido = emissaoPedido.buscarOuFalhar(codigoPedido);
		pedido.confirmar();
		pedidoRepository.save(pedido);
	}
	
	@Transactional
	public void cancelar(String codigoPedido) {
	    Pedido pedido = emissaoPedido.buscarOuFalhar(codigoPedido);
	    pedido.cancelar();
	}
	
	@Transactional
	public void entregar(String codigoPedido) {
		Pedido pedido = emissaoPedido.buscarOuFalhar(codigoPedido);
		pedido.entregar();
	}
}