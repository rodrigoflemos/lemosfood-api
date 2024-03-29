package br.com.lemos.lemosfood.api.v1.controller;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.lemos.lemosfood.api.v1.assembler.PedidoInputDisassembler;
import br.com.lemos.lemosfood.api.v1.assembler.PedidoModelAssembler;
import br.com.lemos.lemosfood.api.v1.assembler.PedidoResumoModelAssembler;
import br.com.lemos.lemosfood.api.v1.model.PedidoModel;
import br.com.lemos.lemosfood.api.v1.model.PedidoResumoModel;
import br.com.lemos.lemosfood.api.v1.model.input.PedidoInput;
import br.com.lemos.lemosfood.api.v1.openapi.controller.PedidoControllerOpenApi;
import br.com.lemos.lemosfood.core.data.PageWrapper;
import br.com.lemos.lemosfood.core.data.PageableTranslator;
import br.com.lemos.lemosfood.core.security.CheckSecurity;
import br.com.lemos.lemosfood.core.security.LemosSecurity;
import br.com.lemos.lemosfood.domain.exception.EntidadeNaoEncontradaException;
import br.com.lemos.lemosfood.domain.exception.NegocioException;
import br.com.lemos.lemosfood.domain.filter.PedidoFilter;
import br.com.lemos.lemosfood.domain.model.Pedido;
import br.com.lemos.lemosfood.domain.model.Usuario;
import br.com.lemos.lemosfood.domain.repository.PedidoRepository;
import br.com.lemos.lemosfood.domain.service.EmissaoPedidoService;
import br.com.lemos.lemosfood.infrastructure.repository.spec.PedidoSpecs;

@RestController
@RequestMapping(path = "/v1/pedidos", produces = MediaType.APPLICATION_JSON_VALUE)
public class PedidoController implements PedidoControllerOpenApi {

	@Autowired
	private PedidoRepository pedidoRepository;

	@Autowired
	private EmissaoPedidoService emissaoPedido;

	@Autowired
	private PedidoModelAssembler pedidoModelAssembler;

	@Autowired
	private PedidoResumoModelAssembler pedidoResumoModelAssembler;

	@Autowired
	private PedidoInputDisassembler pedidoInputDisassembler;
	
	@Autowired
	private PagedResourcesAssembler<Pedido> pagedResourcesAssembler;
	
	@Autowired
	private LemosSecurity lemosSecurity;

	@CheckSecurity.Pedidos.PodePesquisar
	@Override
	@GetMapping
	public PagedModel<PedidoResumoModel> pesquisar(PedidoFilter filtro, 
	        @PageableDefault(size = 10) Pageable pageable) {
	    
		Pageable pageableTraduzido = traduzirPageable(pageable);
	    
	    Page<Pedido> pedidosPage = pedidoRepository.findAll(
	            PedidoSpecs.usandoFiltro(filtro), pageableTraduzido);
	    
	    pedidosPage = new PageWrapper<>(pedidosPage, pageable);
	    
	    return pagedResourcesAssembler.toModel(pedidosPage, pedidoResumoModelAssembler);
	}

//	@GetMapping
//	public MappingJacksonValue listar(@RequestParam(required = false) String campos){
//		List<Pedido> pedidos = pedidoRepository.findAll();
//		List<PedidoResumoModel> pedidosModel = pedidoResumoModelAssembler.toCollectionModel(pedidos);
//		
//		MappingJacksonValue pedidosWrapper = new MappingJacksonValue(pedidosModel);
//		
//		SimpleFilterProvider filterProvider = new SimpleFilterProvider();
//		filterProvider.addFilter("pedidoFilter", SimpleBeanPropertyFilter.serializeAll());
//		
//		if(StringUtils.isNotBlank(campos)) {
//			filterProvider.addFilter("pedidoFilter", SimpleBeanPropertyFilter.filterOutAllExcept(campos.split(",")));
//		}
//		pedidosWrapper.setFilters(filterProvider);
//		
//		return pedidosWrapper;
//	}

	@CheckSecurity.Pedidos.PodeBuscar
	@Override
	@GetMapping("/{codigoPedido}")
	public PedidoModel buscar(@PathVariable String codigoPedido) {
		Pedido pedido = emissaoPedido.buscarOuFalhar(codigoPedido);
		
		return pedidoModelAssembler.toModel(pedido);
	}

	@CheckSecurity.Pedidos.PodeCriar
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public PedidoModel adicionar(@Valid @RequestBody PedidoInput pedidoInput) {
		try {
			Pedido novoPedido = pedidoInputDisassembler.toDomainObject(pedidoInput);

			novoPedido.setCliente(new Usuario());
			novoPedido.getCliente().setId(lemosSecurity.getUsuarioId());

			novoPedido = emissaoPedido.emitir(novoPedido);

			return pedidoModelAssembler.toModel(novoPedido);
		} catch (EntidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	private Pageable traduzirPageable(Pageable pageable) {

		var mapeamento = Map.of(
				"codigo", "codigo",
				"subtotal", "subtotal",
				"taxaFrete", "taxaFrete",
				"valorTotal", "valorTotal",
				"dataCriacao", "dataCriacao",
				"restaurante.nome", "restaurante.nome",
				"restaurante.id", "restaurante.id",
				"cliente.id", "cliente.id",
				"cliente.nome", "cliente.nome"
		);
		
		return PageableTranslator.translate(pageable, mapeamento);
	}
}