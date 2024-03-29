package br.com.lemos.lemosfood.api.v1.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
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

import br.com.lemos.lemosfood.api.v1.assembler.GrupoInputDisassembler;
import br.com.lemos.lemosfood.api.v1.assembler.GrupoModelAssembler;
import br.com.lemos.lemosfood.api.v1.model.GrupoModel;
import br.com.lemos.lemosfood.api.v1.model.input.GrupoInput;
import br.com.lemos.lemosfood.api.v1.openapi.controller.GrupoControllerOpenApi;
import br.com.lemos.lemosfood.core.security.CheckSecurity;
import br.com.lemos.lemosfood.domain.exception.GrupoNaoEncontradoException;
import br.com.lemos.lemosfood.domain.exception.NegocioException;
import br.com.lemos.lemosfood.domain.model.Grupo;
import br.com.lemos.lemosfood.domain.repository.GrupoRepository;
import br.com.lemos.lemosfood.domain.service.CadastroGrupoService;

@RestController
@RequestMapping(path =  "/v1/grupos", produces = MediaType.APPLICATION_JSON_VALUE)
public class GrupoController implements GrupoControllerOpenApi {
    
    @Autowired
    private CadastroGrupoService cadastroGrupo;
    
    @Autowired
    private GrupoModelAssembler grupoModelAssembler;
    
    @Autowired
    private GrupoInputDisassembler grupoInputDisassembler;
    
    @Autowired
    private GrupoRepository grupoRepository;
    
    @CheckSecurity.UsuariosGruposPermissoes.PodeConsultar
    @Override
    @GetMapping
    public CollectionModel<GrupoModel> listar() {
        List<Grupo> todosGrupos = grupoRepository.findAll();
        
        return grupoModelAssembler.toCollectionModel(todosGrupos);
    }
    
    @CheckSecurity.UsuariosGruposPermissoes.PodeConsultar
    @Override
	@GetMapping("/{grupoId}")
    public GrupoModel buscar(@PathVariable Long grupoId) {
        return grupoModelAssembler.toModel(cadastroGrupo.buscarOuFalhar(grupoId));
    }
    
    @CheckSecurity.UsuariosGruposPermissoes.PodeEditar
    @Override
	@PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GrupoModel adicionar(@RequestBody @Valid GrupoInput grupoInput) {
        Grupo grupo = grupoInputDisassembler.toDomainObject(grupoInput);
        
        grupo = cadastroGrupo.salvar(grupo);
        
        return grupoModelAssembler.toModel(grupo);
    }
    
    @CheckSecurity.UsuariosGruposPermissoes.PodeEditar
    @Override
	@PutMapping("/{grupoId}")
    public GrupoModel atualizar(@PathVariable Long grupoId,
            @RequestBody @Valid GrupoInput grupoInput) {
    	
    	Grupo grupoAtual;
    	
    	try {
    		grupoAtual = cadastroGrupo.buscarOuFalhar(grupoId);
    	}catch (GrupoNaoEncontradoException e) {
    		throw new NegocioException(e.getMessage());
		}
    	
        grupoInputDisassembler.copyToDomainObject(grupoInput, grupoAtual);
        
        grupoAtual = cadastroGrupo.salvar(grupoAtual);
        
        return grupoModelAssembler.toModel(grupoAtual);
    }
    
    @CheckSecurity.UsuariosGruposPermissoes.PodeEditar
    @Override
	@DeleteMapping("/{grupoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long grupoId) {
        cadastroGrupo.excluir(grupoId);	
    }   
} 