package br.com.lemos.lemosfood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.lemos.lemosfood.domain.exception.EntidadeNaoEncontradaException;
import br.com.lemos.lemosfood.domain.model.Cozinha;
import br.com.lemos.lemosfood.domain.model.Restaurante;
import br.com.lemos.lemosfood.domain.repository.CozinhaRepository;
import br.com.lemos.lemosfood.domain.repository.RestauranteRepository;

@Service
public class CadastroRestauranteService {
	
	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	public Restaurante salvar(Restaurante restaurante) {
		Long cozinhaId = restaurante.getCozinha().getId();
		Cozinha cozinha = cozinhaRepository.findById(cozinhaId)
				.orElseThrow(() -> new EntidadeNaoEncontradaException(
						String.format("Não existe cadastro de cozinha com código %d", cozinhaId)));
		
		restaurante.setCozinha(cozinha);
		return restauranteRepository.save(restaurante);
	}
	
//	public void excluir(Long restauranteId) {
//		try {
//			restauranteRepository.remover(restauranteId);
//		}catch (EmptyResultDataAccessException e) {
//			throw new EntidadeNaoEncontradaException(
//				String.format("Não existe um cadastro de restaurante com código %d", restauranteId));
//		}catch (DataIntegrityViolationException e) {
//			throw new EntidadeEmUsoException(
//				String.format("Restaurante de código %d não pode ser removido, pois está em uso", restauranteId));
//		}
//	}
}