package br.com.lemos.lemosfood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.lemos.lemosfood.api.assembler.PermissaoModelAssembler;
import br.com.lemos.lemosfood.api.model.PermissaoModel;
import br.com.lemos.lemosfood.api.openapi.controller.GrupoPermissaoControllerOpenApi;
import br.com.lemos.lemosfood.domain.service.CadastroGrupoService;

@RestController
@RequestMapping(path = "/grupos/{grupoId}/permissoes",
        produces = MediaType.APPLICATION_JSON_VALUE)
public class GrupoPermissaoController implements GrupoPermissaoControllerOpenApi {
	@Autowired
	private CadastroGrupoService cadastroGrupo;

	@Autowired
	private PermissaoModelAssembler permissaoModelAssembler;
	
	@GetMapping
	public List<PermissaoModel> listar(@PathVariable Long grupoId) {		
		return permissaoModelAssembler.toCollectionModel(cadastroGrupo.buscarOuFalhar(grupoId).getPermissoes());	
	}
	
	@PutMapping("/{permissaoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void associar(@PathVariable Long grupoId, @PathVariable Long permissaoId) {		
		cadastroGrupo.associarPermissao(grupoId, permissaoId);	
	}
	
	@DeleteMapping("/{permissaoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void desassociar(@PathVariable Long grupoId, @PathVariable Long permissaoId) {		
		cadastroGrupo.desassociarPermissao(grupoId, permissaoId);	
	}
}