package br.com.lemos.lemosfood.api.v1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.lemos.lemosfood.api.v1.LemosLinks;
import br.com.lemos.lemosfood.api.v1.assembler.FormaPagamentoModelAssembler;
import br.com.lemos.lemosfood.api.v1.model.FormaPagamentoModel;
import br.com.lemos.lemosfood.api.v1.openapi.controller.RestauranteFormaPagamentoControllerOpenApi;
import br.com.lemos.lemosfood.core.security.CheckSecurity;
import br.com.lemos.lemosfood.core.security.LemosSecurity;
import br.com.lemos.lemosfood.domain.model.Restaurante;
import br.com.lemos.lemosfood.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping(path = "/v1/restaurantes/{restauranteId}/formas-pagamento",
produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteFormaPagamentoController implements RestauranteFormaPagamentoControllerOpenApi {

	@Autowired
	private CadastroRestauranteService cadastroRestaurante;

	@Autowired
	private FormaPagamentoModelAssembler formaPagamentoModelAssembler;
	
	@Autowired
	private LemosLinks lemosLinks;
	
	@Autowired
	private LemosSecurity lemosSecurity;
	
	@CheckSecurity.Restaurantes.PodeConsultar
	@Override
	@GetMapping
	public CollectionModel<FormaPagamentoModel> listar(@PathVariable Long restauranteId) {
	    Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(restauranteId);
	    
	    CollectionModel<FormaPagamentoModel> formasPagamentoModel 
	        = formaPagamentoModelAssembler.toCollectionModel(restaurante.getFormasPagamento())
	            .removeLinks();
	    
	    formasPagamentoModel.add(lemosLinks.linkToRestauranteFormasPagamento(restauranteId));

	    if (lemosSecurity.podeGerenciarFuncionamentoRestaurantes(restauranteId)) {
	        formasPagamentoModel.add(lemosLinks.linkToRestauranteFormaPagamentoAssociacao(restauranteId, "associar"));
	        
	        formasPagamentoModel.getContent().forEach(formaPagamentoModel -> {
	            formaPagamentoModel.add(lemosLinks.linkToRestauranteFormaPagamentoDesassociacao(
	                    restauranteId, formaPagamentoModel.getId(), "desassociar"));
	        });
	    }
	    
	    return formasPagamentoModel;
	}
	
	@CheckSecurity.Restaurantes.PodeGerenciarFuncionamento
	@DeleteMapping("/{formaPagamentoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> desassociar(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId) {		
		cadastroRestaurante.desassociarFormaPagamento(restauranteId, formaPagamentoId);	
		
		return ResponseEntity.noContent().build();
	}
	
	@CheckSecurity.Restaurantes.PodeGerenciarFuncionamento
	@PutMapping("/{formaPagamentoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> associar(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId) {		
		cadastroRestaurante.associarFormaPagamento(restauranteId, formaPagamentoId);	
		
		return ResponseEntity.noContent().build();

	}
}