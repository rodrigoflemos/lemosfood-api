package br.com.lemos.lemosfood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.ImmutableMap;

import br.com.lemos.lemosfood.api.assembler.PedidoInputDisassembler;
import br.com.lemos.lemosfood.api.assembler.PedidoModelAssembler;
import br.com.lemos.lemosfood.api.assembler.PedidoResumoModelAssembler;
import br.com.lemos.lemosfood.api.model.PedidoModel;
import br.com.lemos.lemosfood.api.model.PedidoResumoModel;
import br.com.lemos.lemosfood.api.model.input.PedidoInput;
import br.com.lemos.lemosfood.core.data.PageableTranslator;
import br.com.lemos.lemosfood.domain.exception.EntidadeNaoEncontradaException;
import br.com.lemos.lemosfood.domain.exception.NegocioException;
import br.com.lemos.lemosfood.domain.model.Pedido;
import br.com.lemos.lemosfood.domain.model.Usuario;
import br.com.lemos.lemosfood.domain.repository.PedidoRepository;
import br.com.lemos.lemosfood.domain.repository.filter.PedidoFilter;
import br.com.lemos.lemosfood.domain.service.EmissaoPedidoService;
import br.com.lemos.lemosfood.infrastructure.repository.spec.PedidoSpecs;

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

	@Autowired
	private PedidoInputDisassembler pedidoInputDisassembler;

	@GetMapping
	public Page<PedidoResumoModel> pesquisar(PedidoFilter filtro, @PageableDefault(size = 10) Pageable pageable) {

		pageable = traduzirPageable(pageable);

		Page<Pedido> pedidosPage = pedidoRepository.findAll(PedidoSpecs.usandoFiltro(filtro), pageable);

		List<PedidoResumoModel> pedidosResumoModel = pedidoResumoModelAssembler
				.toCollectionModel(pedidosPage.getContent());

		Page<PedidoResumoModel> pedidosResumoPage = new PageImpl<>(pedidosResumoModel, pageable,
				pedidosPage.getTotalElements());
		return pedidosResumoPage;
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

	@GetMapping("/{codigoPedido}")
	public PedidoModel buscar(@PathVariable String codigoPedido) {
		return pedidoModelAssembler.toModel(emissaoPedido.buscarOuFalhar(codigoPedido));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public PedidoModel adicionar(@Valid @RequestBody PedidoInput pedidoInput) {
		try {
			Pedido novoPedido = pedidoInputDisassembler.toDomainObject(pedidoInput);

			// TODO pegar usuário autenticado
			novoPedido.setCliente(new Usuario());
			novoPedido.getCliente().setId(1L);

			novoPedido = emissaoPedido.emitir(novoPedido);

			return pedidoModelAssembler.toModel(novoPedido);
		} catch (EntidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	private Pageable traduzirPageable(Pageable pageable) {

		var mapeamento = ImmutableMap.of(
				"codigo", "codigo",
				"restaurante.nome","restaurante.nome",
				"nomeCliente", "cliente.nome",
				"valorTotal", "valorTotal"
		);
		
		return PageableTranslator.translate(pageable, mapeamento);
	}
}