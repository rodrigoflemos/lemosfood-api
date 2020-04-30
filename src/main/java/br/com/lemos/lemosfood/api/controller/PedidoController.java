package br.com.lemos.lemosfood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.lemos.lemosfood.api.assembler.PedidoModelAssembler;
import br.com.lemos.lemosfood.api.assembler.PedidoResumoModelAssembler;
import br.com.lemos.lemosfood.api.model.PedidoModel;
import br.com.lemos.lemosfood.api.model.PedidoResumoModel;
import br.com.lemos.lemosfood.domain.model.Pedido;
import br.com.lemos.lemosfood.domain.repository.PedidoRepository;
import br.com.lemos.lemosfood.domain.service.EmissaoPedidoService;

@RestController
@RequestMapping(value = "/pedidos")
public class PedidoController {

	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private EmissaoPedidoService emissaoPedido;
	
	@Autowired
	private PedidoModelAssembler pedidoModelAssembler;
	
	@Autowired
	private PedidoResumoModelAssembler pedidoResumoModelAssembler;
	
	@GetMapping
	public List<PedidoResumoModel> listar(){
		List<Pedido> todosOsPedidos = pedidoRepository.findAll();
		return pedidoResumoModelAssembler.toCollectionModel(todosOsPedidos);
	}

	@GetMapping("/{pedidoId}")
	public PedidoModel buscar(@PathVariable Long pedidoId) {
		return pedidoModelAssembler.toModel(emissaoPedido.buscarOuFalhar(pedidoId));
	}
}