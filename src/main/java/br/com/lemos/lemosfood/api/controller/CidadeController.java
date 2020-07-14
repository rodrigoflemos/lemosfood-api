package br.com.lemos.lemosfood.api.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.lemos.lemosfood.api.ResourceUriHelper;
import br.com.lemos.lemosfood.api.assembler.CidadeInputDisassembler;
import br.com.lemos.lemosfood.api.assembler.CidadeModelAssembler;
import br.com.lemos.lemosfood.api.model.CidadeModel;
import br.com.lemos.lemosfood.api.model.input.CidadeInput;
import br.com.lemos.lemosfood.api.openapi.controller.CidadeControllerOpenApi;
import br.com.lemos.lemosfood.domain.exception.EstadoNaoEncontradoException;
import br.com.lemos.lemosfood.domain.exception.NegocioException;
import br.com.lemos.lemosfood.domain.model.Cidade;
import br.com.lemos.lemosfood.domain.repository.CidadeRepository;
import br.com.lemos.lemosfood.domain.service.CadastroCidadeService;

@RestController
@RequestMapping(value = "/cidades", produces = MediaType.APPLICATION_JSON_VALUE)
public class CidadeController implements CidadeControllerOpenApi {

	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private CadastroCidadeService cadastroCidade;
	
	@Autowired
	private CidadeModelAssembler cidadeModelAssembler;

	@Autowired
	private CidadeInputDisassembler cidadeInputDisassembler;      
	
	@Override
	@GetMapping
	public List<CidadeModel> listar() {
	    List<Cidade> todasCidades = cidadeRepository.findAll();
	    
	    return cidadeModelAssembler.toCollectionModel(todasCidades);
	}

	@Override
	@GetMapping("/{cidadeId}")
	public CidadeModel buscar(@PathVariable Long cidadeId) {
		Cidade cidade = cadastroCidade.buscarOuFalhar(cidadeId);
		
		CidadeModel cidadeModel = cidadeModelAssembler.toModel(cidade);
		 
		cidadeModel.add(linkTo(methodOn(CidadeController.class)
				.buscar(cidadeModel.getId())).withSelfRel());

		cidadeModel.add(linkTo(methodOn(CidadeController.class)
				.listar()).withRel("cidades"));
		
		cidadeModel.getEstado().add(linkTo(methodOn(EstadoController.class)
				.buscar(cidadeModel.getEstado().getId())).withSelfRel());
		
		return cidadeModel;
	}

	@Override
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CidadeModel adicionar(
			@RequestBody @Valid CidadeInput cidadeInput) {
	    try {
	        Cidade cidade = cidadeInputDisassembler.toDomainObject(cidadeInput);
	  
	        cidade = cadastroCidade.salvar(cidade);
	        
	        CidadeModel cidadeModel = cidadeModelAssembler.toModel(cidade);
	        
	       ResourceUriHelper.addUriInResponseHeader(cidadeModel.getId());
	        							
	        return cidadeModel;
	    } catch (EstadoNaoEncontradoException e) {
	        throw new NegocioException(e.getMessage(), e);
	    }
	}

	@Override
	@PutMapping("/{cidadeId}")
	public CidadeModel atualizar(
			@PathVariable Long cidadeId,
	        @RequestBody @Valid CidadeInput cidadeInput) {
	    try {
	        Cidade cidadeAtual = cadastroCidade.buscarOuFalhar(cidadeId);
	        
	        cidadeInputDisassembler.copyToDomainObject(cidadeInput, cidadeAtual);
	        
	        cidadeAtual = cadastroCidade.salvar(cidadeAtual);
	        
	        return cidadeModelAssembler.toModel(cidadeAtual);
	    } catch (EstadoNaoEncontradoException e) {
	        throw new NegocioException(e.getMessage(), e);
	    }
	}
	
	@Override
	@DeleteMapping("/{cidadeId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(
			@PathVariable Long cidadeId) {
		cadastroCidade.excluir(cidadeId);	
	}
}