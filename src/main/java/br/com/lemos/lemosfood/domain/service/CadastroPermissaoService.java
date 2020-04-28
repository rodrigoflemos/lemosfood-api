package br.com.lemos.lemosfood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.lemos.lemosfood.domain.exception.PermissaoNaoEncontradaException;
import br.com.lemos.lemosfood.domain.model.Permissao;
import br.com.lemos.lemosfood.domain.repository.PermissaoRepository;

@Service
public class CadastroPermissaoService {

	@Autowired
	private PermissaoRepository permissaoRepository;
	
	 public Permissao buscarOuFalhar(Long permissaoId) {
	        return permissaoRepository.findById(permissaoId)
	            .orElseThrow(() -> new PermissaoNaoEncontradaException(permissaoId));
	    }
}