package br.com.lemos.lemosfood.api.v1.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
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
import br.com.lemos.lemosfood.api.v1.assembler.CidadeInputDisassembler;
import br.com.lemos.lemosfood.api.v1.assembler.CidadeModelAssembler;
import br.com.lemos.lemosfood.api.v1.model.CidadeModel;
import br.com.lemos.lemosfood.api.v1.model.input.CidadeInput;
import br.com.lemos.lemosfood.api.v1.openapi.controller.CidadeControllerOpenApi;
import br.com.lemos.lemosfood.core.web.LemosMediaTypes;
import br.com.lemos.lemosfood.domain.exception.EstadoNaoEncontradoException;
import br.com.lemos.lemosfood.domain.exception.NegocioException;
import br.com.lemos.lemosfood.domain.model.Cidade;
import br.com.lemos.lemosfood.domain.repository.CidadeRepository;
import br.com.lemos.lemosfood.domain.service.CadastroCidadeService;

@RestController
@RequestMapping(value = "/cidades", produces = LemosMediaTypes.V1_APPLICATION_JSON_VALUE)
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
	public CollectionModel<CidadeModel> listar() {
	    List<Cidade> todasCidades = cidadeRepository.findAll();
	    
	   return cidadeModelAssembler.toCollectionModel(todasCidades);
	}

	@Override
	@GetMapping("/{cidadeId}")
	public CidadeModel buscar(@PathVariable Long cidadeId) {
		Cidade cidade = cadastroCidade.buscarOuFalhar(cidadeId);
		
		return cidadeModelAssembler.toModel(cidade);
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