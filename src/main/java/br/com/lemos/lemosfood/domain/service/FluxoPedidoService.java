package br.com.lemos.lemosfood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.lemos.lemosfood.domain.model.Pedido;

@Service
public class FluxoPedidoService {

	@Autowired
	private EmissaoPedidoService emissaoPedido;
	
	@Transactional
	public void confirmar(String codigoPedido) {
		
		Pedido pedido = emissaoPedido.buscarOuFalhar(codigoPedido);
		pedido.confirmar();
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