package br.com.lemos.lemosfood.domain.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.lemos.lemosfood.domain.model.FotoProduto;
import br.com.lemos.lemosfood.domain.repository.ProdutoRepository;

@Service
public class CatalogoFotoProdutoService {

	@Autowired
	ProdutoRepository produtoRepository;
	
	@Transactional
	public FotoProduto salvar(FotoProduto foto) {
		
		Long restauranteId = foto.getRestauranteId();
		Long produtoId = foto.getProduto().getId();
		
		Optional<FotoProduto> fotoExistente = produtoRepository.findFotoById(restauranteId, produtoId);
		
		if(fotoExistente.isPresent()) {
			produtoRepository.delete(fotoExistente.get());
		}
		
		return produtoRepository.save(foto);
	}
}
