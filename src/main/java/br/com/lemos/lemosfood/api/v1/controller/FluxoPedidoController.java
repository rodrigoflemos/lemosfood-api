package br.com.lemos.lemosfood.api.v1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.lemos.lemosfood.api.v1.openapi.controller.FluxoPedidoControllerOpenApi;
import br.com.lemos.lemosfood.core.security.CheckSecurity;
import br.com.lemos.lemosfood.domain.service.FluxoPedidoService;

@RestController
@RequestMapping(path = "/v1/pedidos/{codigoPedido}", produces = MediaType.APPLICATION_JSON_VALUE)
public class FluxoPedidoController implements FluxoPedidoControllerOpenApi {
	
	@Autowired
	private FluxoPedidoService fluxoPedido;
	
	@CheckSecurity.Pedidos.PodeGerenciarPedidos
	@PutMapping("/confirmacao")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> confirmar(@PathVariable String codigoPedido) {
		fluxoPedido.confirmar(codigoPedido); 
		
		return ResponseEntity.noContent().build();
	}
	
	@CheckSecurity.Pedidos.PodeGerenciarPedidos
	@PutMapping("/cancelamento")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> cancelar(@PathVariable String codigoPedido) {
	    fluxoPedido.cancelar(codigoPedido);
	    
		return ResponseEntity.noContent().build();

	}

	@CheckSecurity.Pedidos.PodeGerenciarPedidos
	@PutMapping("/entrega")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> entregar(@PathVariable String codigoPedido) {
	    fluxoPedido.entregar(codigoPedido);
	    
		return ResponseEntity.noContent().build();

	}
}