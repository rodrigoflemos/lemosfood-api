package br.com.lemos.lemosfood.domain.service;

import java.io.InputStream;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.lemos.lemosfood.domain.exception.FotoProdutoNaoEncontradaException;
import br.com.lemos.lemosfood.domain.model.FotoProduto;
import br.com.lemos.lemosfood.domain.repository.ProdutoRepository;
import br.com.lemos.lemosfood.domain.service.FotoStorageService.NovaFoto;

@Service
public class CatalogoFotoProdutoService {

	@Autowired
	ProdutoRepository produtoRepository;
	
	@Autowired
	private FotoStorageService fotoStorage;
	
	@Transactional
	public FotoProduto salvar(FotoProduto foto, InputStream dadosArquivo) {
		
		Long restauranteId = foto.getRestauranteId();
		Long produtoId = foto.getProduto().getId();
		String nomeNovoArquivo = fotoStorage.gerarNomeArquivo(foto.getNomeArquivo());
		String nomeArquivoExistente = null;
		
		Optional<FotoProduto> fotoExistente = produtoRepository.findFotoById(restauranteId, produtoId);
		
		if(fotoExistente.isPresent()) {
			produtoRepository.delete(fotoExistente.get());
			nomeArquivoExistente = fotoExistente.get().getNomeArquivo();
		}
		
		foto.setNomeArquivo(nomeNovoArquivo);
		foto = produtoRepository.save(foto);
		produtoRepository.flush();
		
		NovaFoto novaFoto = NovaFoto.builder()
				.nomeArquivo(foto.getNomeArquivo())
				.contentType(foto.getContentType())
				.inputStream(dadosArquivo)
				.build();
				
		fotoStorage.substituir(nomeArquivoExistente, novaFoto);
		
		return foto;
	}
	
	@Transactional
	public void excluir(Long restauranteId, Long produtoId) {
	    FotoProduto foto = buscarOuFalhar(restauranteId, produtoId);
	    
	    produtoRepository.delete(foto);
	    produtoRepository.flush();

	    fotoStorage.remover(foto.getNomeArquivo());
	}
	
	public FotoProduto buscarOuFalhar(Long restauranteId, Long produtoId) {
	    return produtoRepository.findFotoById(restauranteId, produtoId)
	            .orElseThrow(() -> new FotoProdutoNaoEncontradaException(restauranteId, produtoId));
	} 
}